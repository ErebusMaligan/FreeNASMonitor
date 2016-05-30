package modules.control.ui.panel;


import icon.creator.TextIconCreator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modules.cpu.state.monitor.CPUMonitor;
import modules.disk.state.monitor.DiskMonitor;
import modules.disk.state.monitor.RealtimeDiskMonitor;
import modules.mem.state.mem.monitor.MemMonitor;
import modules.net.state.net.monitor.NetworkMonitor;
import statics.GU;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.state.control.Broadcaster;
import fnmcore.state.ssh.SSHSession;
import gui.button.ToggleButton;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 11:03:56 PM 
 */
public class ApplicationControlPanel extends JPanel implements Broadcaster {

	private static final long serialVersionUID = 1L;
	
	private ApplicationState state;
	
	//if these aren't held here, they will stop working because they are weakreferences in applicationstate where they are really held and called to
	//the only purpose of this list is to sit here... but it IS NECESSARY
	private List<BroadcastListener> listeners = new ArrayList<>();

	public ApplicationControlPanel( ApplicationState state ) {
		this.state = state;
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		UIUtils.setColors( this );
		
		//global
		createPanel( "GLOBAL", 
				new SwitchPanel( AC.CON_LIGHTS, e -> state.getUIManager().broadcast( new BroadcastEvent( this, BroadcastEvent.EVENT_TYPE.LIGHT_STATUS,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.EVENT_SETTING.ON : BroadcastEvent.EVENT_SETTING.OFF ) ), true ) 
		);

		//ssh
		List<SwitchPanel> ssh = new ArrayList<>();
		state.getSSHManager().getSSHSessions().forEach( s -> {
			SwitchPanel ret = new SwitchPanel( s.getProcessName(), e -> state.getUIManager().broadcast( new BroadcastEvent( this, SSHSession.class, BroadcastEvent.EVENT_TYPE.SSH_SESSION_COMMAND,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.EVENT_SETTING.CONNECT : BroadcastEvent.EVENT_SETTING.DISCONNECT, s.getProcessName() ) ), AC.AUTO );
			BroadcastListener l = new BroadcastListener() {
				@Override
				public void broadcastReceived( BroadcastEvent e ) {
					if ( e.getEventType().equals( BroadcastEvent.EVENT_TYPE.SSH_SESSION_STATE ) ) {
						if ( e.getAdditional() != null && e.getAdditional().equals( s.getProcessName() ) ) {
							if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.OFF ) ) {
								ret.setState( false );							
							} else if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.ON ) ) {
								ret.setState( true );
							}
						}
					}
				}
			};
			state.getUIManager().addBroadcastListener( l );
			listeners.add( l );
			ssh.add( ret );
		} );
		createPanel( "SSH SESSIONS", ssh.toArray( new SwitchPanel[] {} ) );

		//monitors
		createPanel( "MONITORS",
			createMonitorSwitch( AC.CON_DISK_MON, DiskMonitor.class ),
			createMonitorSwitch( AC.CON_RT_DISK_MON, RealtimeDiskMonitor.class ),
			createMonitorSwitch( AC.CON_CPU_MON, CPUMonitor.class ),
			createMonitorSwitch( AC.CON_MEM_MON, MemMonitor.class ),
			createMonitorSwitch( AC.CON_NET_MON, NetworkMonitor.class )
		);
		
	}
	
	private void createPanel( String name, Component...c ) {
		JPanel main = new JPanel( new BorderLayout() );
		JPanel title = new JPanel( new BorderLayout() );
		JLabel t = new JLabel( new TextIconCreator().getOn( name, new JLabel().getFont(), AC.FOREGROUND, AC.BACKGROUND ) );
		title.add( t, BorderLayout.CENTER );
		main.add( title, BorderLayout.NORTH );
		main.setBorder( BorderFactory.createLineBorder( AC.FOREGROUND_DARKER ) );
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
		SwitchPanel ret = new SwitchPanel( text, getMonitorEvent( c ), AC.AUTO );
		BroadcastListener l = new BroadcastListener() {
			@Override
			public void broadcastReceived( BroadcastEvent e ) {
				if ( e.getEventType().equals( BroadcastEvent.EVENT_TYPE.MONITOR_STATE ) ) {
					if ( e.getSource().getClass().getName().equals( c.getName() ) ) {
						if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.OFF ) ) {
							ret.setState( false );							
						} else if ( e.getEventSetting().equals( BroadcastEvent.EVENT_SETTING.ON ) ) {
							ret.setState( true );
						}
					}
				}
			}
		};
		state.getUIManager().addBroadcastListener( l );
		listeners.add( l );
		return ret;
	}
	
	private ActionListener getMonitorEvent( Class<?> c ) {
		return e -> state.getUIManager().broadcast( new BroadcastEvent( this, c, BroadcastEvent.EVENT_TYPE.MONITOR_COMMAND,  ( (ToggleButton)e.getSource() ).isSelected() ? BroadcastEvent.EVENT_SETTING.START : BroadcastEvent.EVENT_SETTING.STOP ) );
	}
}