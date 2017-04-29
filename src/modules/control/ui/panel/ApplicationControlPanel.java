package modules.control.ui.panel;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fnmcore.constants.ApplicationConstants;
import gui.button.ToggleButton;
import icon.creator.TextIconCreator;
import modules.cpu.state.monitor.CPUMonitor;
import modules.disk.state.monitor.DiskMonitor;
import modules.disk.state.monitor.RealtimeDiskMonitor;
import modules.mem.state.mem.monitor.MemMonitor;
import modules.net.state.net.monitor.NetworkMonitor;
import ssh.SSHSession;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.control.Broadcaster;
import state.provider.ApplicationProvider;
import state.provider.ProviderConstants;
import statics.GU;
import statics.UIUtils;
import ui.theme.ThemeConstants;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 11:03:56 PM 
 */
public class ApplicationControlPanel extends JPanel implements Broadcaster {

	private static final long serialVersionUID = 1L;
	
	private ApplicationProvider state;
	
	//if these aren't held here, they will stop working because they are weakreferences in ApplicationProvider where they are really held and called to
	//the only purpose of this list is to sit here... but it IS NECESSARY
	private List<BroadcastListener> listeners = new ArrayList<>();

	public ApplicationControlPanel( ApplicationProvider state ) {
		this.state = state;
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		UIUtils.setColors( this );
		
		//global
		createPanel( "GLOBAL", 
				new SwitchPanel( ApplicationConstants.CON_LIGHTS, e -> state.broadcast( new BroadcastEvent( this, BroadcastEvent.LIGHT_STATUS,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.ON : BroadcastEvent.OFF ) ), true ) 
		);

		//ssh
		List<SwitchPanel> ssh = new ArrayList<>();
		state.getSSHManager().getSSHSessions().forEach( s -> {
			SwitchPanel ret = new SwitchPanel( s.getProcessName(), e -> state.broadcast( new BroadcastEvent( this, SSHSession.class, BroadcastEvent.SSH_SESSION_COMMAND,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.CONNECT : BroadcastEvent.DISCONNECT, s.getProcessName() ) ), ProviderConstants.AUTO );
			BroadcastListener l = new BroadcastListener() {
				@Override
				public void broadcastReceived( BroadcastEvent e ) {
					if ( e.getEventType().equals( BroadcastEvent.SSH_SESSION_STATE ) ) {
						if ( e.getAdditional() != null && e.getAdditional().equals( s.getProcessName() ) ) {
							if ( e.getEventSetting().equals( BroadcastEvent.OFF ) ) {
								ret.setState( false );							
							} else if ( e.getEventSetting().equals( BroadcastEvent.ON ) ) {
								ret.setState( true );
							}
						}
					}
				}
			};
			state.addBroadcastListener( l );
			listeners.add( l );
			ssh.add( ret );
		} );
		createPanel( "SSH SESSIONS", ssh.toArray( new SwitchPanel[] {} ) );

		//monitors
		createPanel( "MONITORS",
			createMonitorSwitch( ApplicationConstants.CON_DISK_MON, DiskMonitor.class ),
			createMonitorSwitch( ApplicationConstants.CON_RT_DISK_MON, RealtimeDiskMonitor.class ),
			createMonitorSwitch( ApplicationConstants.CON_CPU_MON, CPUMonitor.class ),
			createMonitorSwitch( ApplicationConstants.CON_MEM_MON, MemMonitor.class ),
			createMonitorSwitch( ApplicationConstants.CON_NET_MON, NetworkMonitor.class )
		);
		
	}
	
	private void createPanel( String name, Component...c ) {
		JPanel main = new JPanel( new BorderLayout() );
		JPanel title = new JPanel( new BorderLayout() );
		JLabel t = new JLabel( new TextIconCreator().getOn( name, new JLabel().getFont(), ThemeConstants.FOREGROUND, ThemeConstants.BACKGROUND ) );
		title.add( t, BorderLayout.CENTER );
		main.add( title, BorderLayout.NORTH );
		main.setBorder( BorderFactory.createLineBorder( ThemeConstants.FOREGROUND_DARKER ) );
		JPanel p = new JPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.X_AXIS ) );
		for ( Component x : c ) {
			p.add( x );
		}
		GU.hGlue2( p );
		main.add( p, BorderLayout.CENTER );
		this.add( main );
		UIUtils.setColors( main, title, t, p );
	}
	
	private SwitchPanel createMonitorSwitch( String text, Class<?> c ) {
		SwitchPanel ret = new SwitchPanel( text, getMonitorEvent( c ), ProviderConstants.AUTO );
		BroadcastListener l = new BroadcastListener() {
			@Override
			public void broadcastReceived( BroadcastEvent e ) {
				if ( e.getEventType().equals( BroadcastEvent.MONITOR_STATE ) ) {
					if ( e.getSource().getClass().getName().equals( c.getName() ) ) {
						if ( e.getEventSetting().equals( BroadcastEvent.OFF ) ) {
							ret.setState( false );							
						} else if ( e.getEventSetting().equals( BroadcastEvent.ON ) ) {
							ret.setState( true );
						}
					}
				}
			}
		};
		state.addBroadcastListener( l );
		listeners.add( l );
		return ret;
	}
	
	private ActionListener getMonitorEvent( Class<?> c ) {
		return e -> state.broadcast( new BroadcastEvent( this, c, BroadcastEvent.MONITOR_COMMAND,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.START : BroadcastEvent.STOP ) );
	}
}