package fnmcore.state.ssh;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import process.ProcessManager;
import process.TerminalProcess;
import process.io.ProcessStreamSiphon;
import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.state.control.Broadcaster;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 9:56:31 PM 
 */
public class SSHSession extends Observable implements ProcessStreamSiphon, BroadcastListener, Broadcaster {
	
	boolean connected = false;
	
	private String processName;
	
	private final Semaphore lock = new Semaphore( 1 );
	
	private final Semaphore monitorLock = new Semaphore( 1 );
	
	private boolean connecting = false;
	
	private boolean disconnecting = false;
	
	private Object startLock = new Object();
	
	private boolean started = false;
	
	private ApplicationState state;
	
	public SSHSession( ApplicationState state, String processName ) {
		this.state = state;
		this.processName = processName;
		ProcessManager.getInstance().registerSiphon( processName, this );
	}
	
	private void restartProcess() {
		if ( ProcessManager.getInstance().getProcessByName( processName ) == null ) {
			started = false;
			new TerminalProcess( processName );
		} else {
			started = true;
		}
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public CountDownLatch getLock() throws InterruptedException {
		lock.acquire();
		return new CountDownLatch( 1 );
	}
	
	public void returnLock() {
		lock.release();
	}
	
	public void getMonitorLock() throws InterruptedException {
		monitorLock.acquire();
	}
	
	public void returnMonitorLock() {
		monitorLock.release();
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public boolean isConnecting() {
		return connecting;
	}
	
	public boolean sendCommand( String command ) {
		boolean ret = false;
		try {
			ProcessManager.getInstance().getProcessByName( processName ).sendCommand( command );
			ret = true;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void disconnect() {
		if ( connected && !disconnecting ) {
			disconnecting = true;
			sendCommand( AC.SSH_EXIT );
			sendCommand( AC.SSH_EXIT );  //send logout twice; once to log out of root, back to normal user, then once to actually logout completely
		}
	}
	
	public void connect() {
		if ( !connected && !connecting && !disconnecting ) {
			System.out.println( "SSH Session " + processName + " Attempting Connection..." );
			connecting = true;
			restartProcess();
			synchronized( startLock ) {
				if ( started ) {
					if ( ( TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ) ) {
						ProcessManager.getInstance().getProcessByName( processName ).sendCommand( TerminalProcess.getOSSettings().issueCDCommand( AC.SSH_APP_PATH ) );
						sendCommand( AC.SSH_APPLICATION + " " + AC.SSH_HOST_COMMAND + " " + AC.SSH_USER + AC.SSH_AT + AC.SSH_IP + " " + AC.SSH_PORT_COMMAND + " " + AC.SSH_PORT + " " 
					+ ( AC.SSH_USE_KEY ? AC.SSH_KEY_COMMAND + " " + AC.SSH_KEY_PATH : AC.SSH_PW_COMMAND + " " + AC.SSH_PW ) );
						started = false;
					} else {
						sendCommand( AC.SSH_APPLICATION + " " + AC.SSH_USER + AC.SSH_AT + AC.SSH_IP + " " + AC.SSH_TTY_FIX + " " + AC.SSH_PORT_COMMAND + " " + AC.SSH_PORT + " " 
								+ ( AC.SSH_USE_KEY ? AC.SSH_KEY_COMMAND + " " + AC.SSH_KEY_PATH : "" ) ); //can't provide password in linux ssh
					}
				}
			}
		}
	}
	
	@Override
	public void skimMessage( String name, String line ) {
		if ( line.contains( "Welcome to FreeNAS" ) ) {  //moved the actual connection accept stuff to check for Password first
			sendCommand( "sudo -iS" );
			sendCommand( AC.SSH_PW );
			new Thread( () -> {
				try {
					Thread.sleep( 1000 );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				connected = true;
				connecting = false;
				System.out.println( "SSH Session " + processName + " Connected" );
				changed();
			} ).start();
		} else if ( line.contains( "Using username" ) ) {
			System.out.println( "SSH Session " + processName + " Disconnected" );
			shutdownProcess();
			connected = false;
			disconnecting = false;
			changed();
		}
	}
	
	public void shutdownProcess() {
		try {
			TerminalProcess t = ( (TerminalProcess)ProcessManager.getInstance().getProcessByName( processName ) );
			if ( t != null ) {  //will be null if the SSH session has been turned off (manually or otherwise) and never restarted
				t.closeResources();
				while ( !t.isTerminated() ) {
					try {
						Thread.sleep( 100 );
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void changed() {
		setChanged();
		notifyObservers( connected );
		state.getUIManager().broadcast( new BroadcastEvent( this, BroadcastEvent.EVENT_TYPE.SSH_SESSION_STATE, connected ? BroadcastEvent.EVENT_SETTING.ON : BroadcastEvent.EVENT_SETTING.OFF, processName ) );
	}
	
	@Override
	public void notifyProcessEnded( String name ) {
		started = false;
		connected = false;
		disconnecting = false;
		changed();
	}

	@Override
	public void notifyProcessStarted( String name ) {
		synchronized( startLock ) {
			started = true;
			if ( connecting ) {
				ProcessManager.getInstance().getProcessByName( processName ).sendCommand( TerminalProcess.getOSSettings().issueCDCommand( AC.SSH_APP_PATH ) );
				sendCommand( AC.SSH_APPLICATION + " " + AC.SSH_HOST_COMMAND + " " + AC.SSH_USER + AC.SSH_AT + AC.SSH_IP + " " + AC.SSH_PORT_COMMAND + " " + AC.SSH_PORT + " " 
			+ ( AC.SSH_USE_KEY ? AC.SSH_KEY_COMMAND + " " + AC.SSH_KEY_PATH : AC.SSH_PW_COMMAND + " " + AC.SSH_PW ) );
				started = false;
			}
		}
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getDestination() != null ) {
			if ( e.getDestination().getName().equals( this.getClass().getName() ) ) {
				if ( e.getEventType().equals( BroadcastEvent.EVENT_TYPE.SSH_SESSION_COMMAND ) ) {
					if ( e.getAdditional() != null ) {
						if ( e.getAdditional().equals( processName ) ) {
							if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.CONNECT ) ) {
								this.connect();
							} else if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.DISCONNECT ) ) {
								this.disconnect();
							}
						}
					}
				}
			}
		}
	}	
}