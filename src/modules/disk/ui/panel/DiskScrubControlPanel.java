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

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.layout.WrapLayout;
import gui.progress.EnhancedJProgressBar;
import modules.disk.module.DiskConstants;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.GU;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 17, 2015, 1:46:56 AM 
 */
public class DiskScrubControlPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private ApplicationProvider state;
	
	private List<String> pools = new ArrayList<String>();
	
	public DiskScrubControlPanel( ApplicationProvider state ) {
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
		
		public ScrubChart( ApplicationProvider state, ScrubInfo si ) {
			super( state, state.getMonitorManager().getMonitorByName( DiskModule.RT_DISK_MONITOR ) );
			this.si = si;
			this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
			JLabel l = new JLabel( si.pool );
			l.setHorizontalAlignment( SwingConstants.CENTER );
			start = GU.createButton( DiskConstants.B_DISK_SCRUB_START, e -> state.getSSHManager().getSSHSession( ApplicationConstants.SSH_RT_PROCESS_NAME ).sendCommand( DiskConstants.DISK_SCRUB_START + si.pool ) );
			stop = GU.createButton(  DiskConstants.B_DISK_SCRUB_STOP, e -> state.getSSHManager().getSSHSession( ApplicationConstants.SSH_RT_PROCESS_NAME ).sendCommand( DiskConstants.DISK_SCRUB_STOP + si.pool ) );
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
			state.addBroadcastListener( this );
		}
		
		protected void calculateSections( EnhancedJProgressBar bar ) {
		}
		
		private void enableStart() {
			start.setEnabled( true );
			start.setForeground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND );
			stop.setEnabled( false );
			stop.setForeground( lightsOff ? UIUtils.lightsOff( Color.DARK_GRAY, ApplicationConstants.LIGHTS_OFF ) : Color.DARK_GRAY );
		}
		
		private void enableStop() {
			start.setEnabled( false );
			start.setForeground( lightsOff ? UIUtils.lightsOff( Color.DARK_GRAY, ApplicationConstants.LIGHTS_OFF ) : Color.DARK_GRAY );
			stop.setEnabled( true );
			stop.setForeground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND );
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
			if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
				if ( e.getEventSetting() == BroadcastEvent.ON ) {
					UIUtils.setColorsRecursive( this );
					for ( JButton b : Arrays.asList( start, stop ) ) {
						b.setForeground( UIUtils.lightsOn( b.getForeground(), ApplicationConstants.LIGHTS_OFF ) );
						b.setBackground( UIUtils.lightsOn( b.getBackground(), ApplicationConstants.LIGHTS_OFF ) );
						b.setBorder( BorderFactory.createLineBorder( ThemeConstants.FOREGROUND.darker().darker().darker() ) );						
					}
					lightsOff = false;
				} else {
					UIUtils.setColorsRecursiveOff( this );
					for ( JButton b : Arrays.asList( start, stop ) ) {
						b.setForeground( UIUtils.lightsOff( b.getForeground(), ApplicationConstants.LIGHTS_OFF ) );
						b.setBackground( UIUtils.lightsOff( b.getBackground(), ApplicationConstants.LIGHTS_OFF ) );
						b.setBorder( BorderFactory.createLineBorder( UIUtils.lightsOff( ThemeConstants.FOREGROUND.darker().darker().darker(), ApplicationConstants.LIGHTS_OFF ) ) );						
					}
					lightsOff = true;
				}

			}
		}
	}
}