package fnmcore.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.menu.MonitorMenuBar;
import gui.dialog.BusyDialog;
import gui.progress.DigitalJProgressBar;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 7:48:08 PM 
 */
public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MonitorFrame( ApplicationState state, GraphicsDevice d ) {
		super( d.getDefaultConfiguration() );
		setExtendedState( AC.FRAME_MAX_STATE );
		setLocation( AC.FRAME_X, AC.FRAME_Y );
		setSize( AC.FRAME_W, AC.FRAME_H );
		setTitle( "FreeNAS Monitor" );
		setIconImage( AC.APP_ICON.getImage() );
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				new BusyDialog( MonitorFrame.this, "Halting Monitor Threads" ) {
					private static final long serialVersionUID = 1L;
					@Override
					public void executeTask() {
						state.getUIManager().shutdownComponents();
						state.getMonitorManager().stopAllMonitors();
						if ( !state.getMonitorManager().monitorsHalted() ) {
							try {
								Thread.sleep( 3500 );  //give 3.5 seconds to shutdown normally... then force shutdown
							} catch ( InterruptedException e1 ) {
								e1.printStackTrace();
							}
						}
						state.getMonitorManager().foricblyStopAllMonitors();
						while ( !state.getMonitorManager().monitorsHalted() ) {
							try {
								Thread.sleep( 10 );
							} catch ( InterruptedException e1 ) {
								e1.printStackTrace();
							}
						}
					}
					
					@Override
					public JProgressBar getProgressBar() {
						DigitalJProgressBar bar = new DigitalJProgressBar( 0, 1000 );
						bar.setSegments( 10 );
						UIUtils.setColors( bar );
						return bar;
					}
				};
				new BusyDialog( MonitorFrame.this, "Disconnecting SSH Sessions" ) {
					private static final long serialVersionUID = 1L;
					@Override
					public void executeTask() {
						state.getSSHManager().shutdownSSHSessions();
					}
					
					@Override
					public JProgressBar getProgressBar() {
						DigitalJProgressBar bar = new DigitalJProgressBar( 0, 1000 );
						bar.setSegments( 10 );
						UIUtils.setColors( bar );
						return bar;
					}
				};
				state.getUIManager().getTabManager().getAllWindows().forEach( w -> { 
					try {
						w.setClosed( true );
					} catch ( Exception e1 ) {
						e1.printStackTrace();
					}
				} );
				MonitorFrame.this.dispose();
			}
		} );
		setLayout( new BorderLayout() );
		add( state.getUIManager().getTabManager().getTabPane(), BorderLayout.CENTER );
		add( new MonitorMenuBar( state ), BorderLayout.NORTH );
		UIUtils.setColors( this.getContentPane() );		
		setVisible( true );
	}
}