package modules.disk.ui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.layout.WrapLayout;
import gui.progress.EnhancedJProgressBar;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
import statics.GU;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 17, 2015, 1:46:56 AM 
 */
public class DiskScrubControlPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private ApplicationState state;
	
	private List<String> pools = new ArrayList<String>();
	
	public DiskScrubControlPanel( ApplicationState state ) {
		this.state = state;
//		this.setLayout( new GridLayout( 4, 0 ) );
//		this.setLayout( new GridLayout( 2, 0 ) );
		this.setLayout( new WrapLayout() );
		UIUtils.setColors( this );
		state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ).addObserver( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		for ( String p : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getScrubs().keySet() ) {
			ScrubInfo si = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getScrubs().get( p );
			if ( si != null ) {
				if ( !pools.contains( si.pool ) ) {
					pools.add( si.pool );
					add( new ScrubChart( state, si ) );
				}
			}
		}
	}
	
	
	private class ScrubChart extends SimpleChart implements BroadcastListener {
	
		private static final long serialVersionUID = 1L;

		private JProgressBar bar;
		
		private ScrubInfo si;
		
		private JButton start, stop;
		
		protected boolean lightsOff = false;
		
		public ScrubChart( ApplicationState state, ScrubInfo si ) {
			super( state, state.getMonitorManager().getMonitorByName( DiskModule.RT_DISK_MONITOR ) );
			this.si = si;
			this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
			JLabel l = new JLabel( si.pool );
			l.setHorizontalAlignment( SwingConstants.CENTER );
			start = GU.createButton( AC.B_DISK_SCRUB_START, e -> state.getSSHManager().getSSHSession( AC.SSH_RT_PROCESS_NAME ).sendCommand( AC.DISK_SCRUB_START + si.pool ) );
			stop = GU.createButton(  AC.B_DISK_SCRUB_STOP, e -> state.getSSHManager().getSSHSession( AC.SSH_RT_PROCESS_NAME ).sendCommand( AC.DISK_SCRUB_STOP + si.pool ) );
			UIUtils.setJButton( start );
			UIUtils.setJButton( stop );
			start.addActionListener( e -> { enableStop(); si.done = -1; } );
			stop.addActionListener( e -> enableStart() );
			dim = new Dimension( 50, 16 );
			JPanel hp = GU.hp( this, new Dimension( 2, 2 ), new Object[] { l, new Dimension( 80, 16 ) } );
			enableStart();
			
			dim = new Dimension( 80, 10 );
			height = 12;
			width = 35;
			bar = addJProgressBar( 0, 100, "Done", "%" );
			this.add( center );
			dim = new Dimension( 50, 16 );
			JPanel hp2 = GU.hp( this, new Dimension( 2, 2 ), new Object[] { start, dim }, new Object[] { stop, dim } );
			UIUtils.setColors( l, start, stop, hp, hp2 );
			state.getUIManager().addBroadcastListener( this );
		}
		
		protected void calculateSections( EnhancedJProgressBar bar ) {
		}
		
		private void enableStart() {
			start.setEnabled( true );
			start.setForeground( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND, AC.LIGHTS_OFF ) : AC.FOREGROUND );
			stop.setEnabled( false );
			stop.setForeground( lightsOff ? UIUtils.lightsOff( Color.DARK_GRAY, AC.LIGHTS_OFF ) : Color.DARK_GRAY );
		}
		
		private void enableStop() {
			start.setEnabled( false );
			start.setForeground( lightsOff ? UIUtils.lightsOff( Color.DARK_GRAY, AC.LIGHTS_OFF ) : Color.DARK_GRAY );
			stop.setEnabled( true );
			stop.setForeground( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND, AC.LIGHTS_OFF ) : AC.FOREGROUND );
		}

		@Override
		public void update( Observable o, Object arg ) {
			int done = 0;
			if ( si.done > 0 ) {
				done = new Float( si.done ).intValue();
			}
			if ( done != 0 && done != 100 ) {
				enableStop();
			} else {
				enableStart();
			}
			bar.setValue( done );
		}

		@Override
		public void broadcastReceived( BroadcastEvent e ) {
			if ( e.getEventType() == BroadcastEvent.EVENT_TYPE.LIGHT_STATUS ) {
				if ( e.getEventSetting() == BroadcastEvent.EVENT_SETTING.ON ) {
					UIUtils.setColorsRecursive( this );
					for ( JButton b : Arrays.asList( start, stop ) ) {
						b.setForeground( UIUtils.lightsOn( b.getForeground(), AC.LIGHTS_OFF ) );
						b.setBackground( UIUtils.lightsOn( b.getBackground(), AC.LIGHTS_OFF ) );
						b.setBorder( BorderFactory.createLineBorder( AC.FOREGROUND.darker().darker().darker() ) );						
					}
					lightsOff = false;
				} else {
					UIUtils.setColorsRecursiveOff( this );
					for ( JButton b : Arrays.asList( start, stop ) ) {
						b.setForeground( UIUtils.lightsOff( b.getForeground(), AC.LIGHTS_OFF ) );
						b.setBackground( UIUtils.lightsOff( b.getBackground(), AC.LIGHTS_OFF ) );
						b.setBorder( BorderFactory.createLineBorder( UIUtils.lightsOff( AC.FOREGROUND.darker().darker().darker(), AC.LIGHTS_OFF ) ) );						
					}
					lightsOff = true;
				}

			}
		}
	}
}