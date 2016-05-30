package fnmcore.state.monitor;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.state.control.Broadcaster;
import fnmcore.state.ssh.SSHSession;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 11:17:02 PM 
 */
public abstract class AbstractMonitor extends Observable implements BroadcastListener, Broadcaster {
	
	protected boolean kill = true;
	
	protected ApplicationState state;
	
	protected SSHSession ssh;
	
	protected Thread monitor;
	
	protected MonitorData lh;
	
	protected long interval;
	
	protected boolean halted = true;
	
	int printLoopInfoEvery = 10;
	
	public AbstractMonitor( ApplicationState state, MonitorData lh, SSHSession ssh, long interval ) {
		this.state = state;
		this.lh = lh;
		this.ssh = ssh;
		this.interval = interval;
		state.getUIManager().addBroadcastListener( this );
	}

	public void start() {
		lh.resetWinError();
		if ( kill = true && ssh != null ) {
			kill = false;
			halted = false;
			int counter = 0;
			int max = 200;
			while ( !kill && !ssh.isConnected() && counter < max ) { //spinlock until ssh thread is properly connected and sudo issued
				try {
					Thread.sleep( 10 );
					counter++;
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
			if ( counter != max - 1 ) {  //if the ssh session hasn't been even started yet, it probably wasn't going to be, drop out and make the user restart
				monitor = new Thread( new Runnable() {
					boolean ranOnce = false;
					public void run() {
						state.getUIManager().broadcast( new BroadcastEvent( AbstractMonitor.this, BroadcastEvent.EVENT_TYPE.MONITOR_STATE, BroadcastEvent.EVENT_SETTING.ON ) );
						try {
							ssh.getMonitorLock();
							System.out.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  running initial commands..." );
							
							//run initial commands
							runOnce();
							
							System.out.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  finished initial commands." );
							if ( kill ) {
								ssh.returnMonitorLock();
							}
							int i = 0;
							while( !kill ) {
								if ( ranOnce ) {
									ssh.getMonitorLock();
								}
								i++;
								if ( i == printLoopInfoEvery ) {
									System.out.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  running standard commands..." );
								}
								runLoop();
								if ( i == printLoopInfoEvery ) {
									System.out.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  finished standard commands." );
									i = 0;
								}
								ranOnce = true;
								ssh.returnMonitorLock();
								setChanged();
								try {
									notifyObservers( null );
								} catch ( Exception e ) {
									e.printStackTrace();
								}
								Thread.sleep( interval );
							}
							System.out.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  exited normally." );
						} catch ( InterruptedException e ) {
							ssh.returnMonitorLock(); //do this or other monitor threads will deadlock
							System.err.println( "MONITOR: " + AbstractMonitor.this.getClass().getName() + "  forced to exit." );
						}
						halted = true;
						state.getUIManager().broadcast( new BroadcastEvent( AbstractMonitor.this, BroadcastEvent.EVENT_TYPE.MONITOR_STATE, BroadcastEvent.EVENT_SETTING.OFF ) );
					};
				} );
				monitor.start();
			}
		}
	}
	
	public void interrupt() {
		if ( monitor != null && monitor.isAlive() ) {
			monitor.interrupt();
		}
	}
	
	protected CDLAction createAction( String base, String command ) {
		return () -> { 
			lh.setActiveSkimmer( base );
			boolean sshAlive = ssh.sendCommand( command );
			if ( !sshAlive ) {
				deadSSH();
			} else {
				ssh.sendCommand( AC.ECHO_CMD + ( base.contains( "grep" ) ? " " : " " + base + " " ) + AC.COMPLETE );
			}
		};
	}
	
	protected void handleCDL( CDLAction a ) {
		if ( !kill ) {
			try {
				CountDownLatch cdl = ssh.getLock();
				lh.giveLock( cdl );
				a.execute();
				cdl.await();
				lh.setActiveSkimmer( null );
				lh.giveLock( null );
				if ( lh.didWinError() ) {
					stopRunning();
				}
				ssh.returnLock();
			} catch ( InterruptedException e ) {
				ssh.returnLock();  //do this or other monitor threads will not be able to execute any actions
				e.printStackTrace();
			}
		}
	}
	
	private void deadSSH() {
		System.err.println( "SSH Session unavailable.  Stopping monitor" );
		stopRunning();
		lh.deadSSH();
	}
	
	public void stopRunning() {
		kill = true;
	}
	
	public boolean isHalted() {
		return halted;
	}
	
	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getDestination() != null ) {
			if ( this.getClass().getName().equals( e.getDestination().getName() ) ) {
				if ( e.getEventType().equals( BroadcastEvent.EVENT_TYPE.MONITOR_COMMAND ) ) {
					if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.START ) ) {
						this.start();
					} else if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.STOP ) ) {
						this.stopRunning();
					}
				}
			}
		}
	}
	
	protected abstract void runOnce() throws InterruptedException;
	
	protected abstract void runLoop() throws InterruptedException;
}