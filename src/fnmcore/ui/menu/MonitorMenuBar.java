package fnmcore.ui.menu;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.plaf.basic.BasicMenuBarUI;

import fnmcore.constants.ApplicationConstants;
import gui.dialog.OKCancelDialog;
import gui.menubar.GenericActionListener;
import gui.menubar.GenericMenuBar;
import gui.menubar.GenericMenuBarAction;
import modules.cpu.module.CPUConstants;
import modules.disk.module.DiskConstants;
import modules.mem.module.MemConstants;
import modules.net.module.NetConstants;
import ssh.SSHConstants;
import ssh.SSHSession;
import state.provider.ApplicationProvider;
import state.provider.ProviderConstants;
import statics.LAFUtils;
import statics.UIUtils;
import ui.log.OutputLogConstants;
import ui.ssh.SSHDialog;
import ui.theme.ThemeConstants;
import ui.window.CreateTabDialog;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 8:51:30 PM 
 */
public class MonitorMenuBar extends GenericMenuBar implements Observer {

	private static final long serialVersionUID = 1L;

	private ApplicationProvider state;

	public MonitorMenuBar( ApplicationProvider state ) {
		this.state = state;
		for ( SSHSession ssh : state.getSSHManager().getSSHSessions() ) {
			ssh.addObserver( this );
		}
		createSSHMenu();
		createSystemMonitorMenu();
		createSettingsMenu();
		createWindowsMenu();
		this.add( Box.createGlue() );
		//		createLogMenu();
		update( null, false );

		this.setUI( new BasicMenuBarUI() );
		this.setMinimumSize( new Dimension( 0, 22 ) );
		this.setPreferredSize( new Dimension( this.getWidth(), 22 ) );
		LAFUtils.applyThemedUI( this, ThemeConstants.BACKGROUND, ThemeConstants.FOREGROUND );

		UIUtils.setColors( this );
	}

	private void createSSHMenu() {
		menu = new JMenu( ApplicationConstants.MB_SSH );
		UIUtils.setColors( menu );
		createItem( ApplicationConstants.MB_SSH_CONNECT, o -> {
			for ( SSHSession ssh : state.getSSHManager().getSSHSessions() ) {
				ssh.connect();
			}
		} );
		menu.add( new JSeparator() );
		createItem( ApplicationConstants.MB_SSH_DISCONNECT, o -> {
			for ( SSHSession ssh : state.getSSHManager().getSSHSessions() ) {
				ssh.disconnect();
			}
		} );
		this.add( menu );
	}

	private void createSystemMonitorMenu() {
		menu = new JMenu( ApplicationConstants.MB_SYS_MONITOR );
		UIUtils.setColors( menu );
		createItem( ApplicationConstants.MB_SYS_MONITOR_START, o -> state.getMonitorManager().startAllMonitors() );
		menu.add( new JSeparator() );
		createItem( ApplicationConstants.MB_SYS_MONITOR_STOP, o -> state.getMonitorManager().stopAllMonitors() );
		this.add( menu );
	}

	private void createSettingsMenu() {
		menu = new JMenu( ApplicationConstants.MB_SETTINGS );
		UIUtils.setColors( menu );
		createCheckItem( ApplicationConstants.MB_AUTO_START, o -> {
			ProviderConstants.AUTO = !ProviderConstants.AUTO;
			state.writeSettings();
		}, ProviderConstants.AUTO );
		menu.add( new JSeparator() );
		createItem( ApplicationConstants.MB_FRAME, o -> state.getFrame().saveWindowSettings( state ) );
		createItem( ApplicationConstants.MB_SSH, o -> new SSHDialog( state.getFrame(), state ).setVisible( true ) );
		createItem( ApplicationConstants.MB_COLOR, o -> new ColorDialog( state ).setVisible( true ) );
		createItem( ApplicationConstants.MB_MONITOR_INTERVALS, o -> new IntervalDialog( state ).setVisible( true ) );
		createItem( ApplicationConstants.MB_NETWORK_INTERFACE, o -> new NetworkInterfaceDialog( state ).setVisible( true ) );
//		menu.add( new JSeparator() );  //setting debug mode works, but at the moment there is nothing that turns on as a result of the setting
//		createCheckItem( ApplicationConstants.MB_DEBUG_MODE, o -> {
//			ApplicationConstants.DEBUG = !ApplicationConstants.DEBUG;
//			state.writeSettings();
//		}, ApplicationConstants.DEBUG );
		this.add( menu );
	}

	private void createWindowsMenu() {
		JMenu win = menu = new JMenu( ApplicationConstants.MB_WINDOW_MANAGEMENT );
		UIUtils.setColors( menu );
		menu = new JMenu( ApplicationConstants.MB_TABS );
		UIUtils.setColors( menu );
		createItem( ApplicationConstants.MB_CREATE_TAB, o -> {
			CreateTabDialog t = new CreateTabDialog( state.getFrame() );
			t.setVisible( true );
			if ( t.getResult() == OKCancelDialog.OK ) {
				state.getTabManager().addTab( t.getTabName() );
			}
		} );
		createItem( ApplicationConstants.MB_REMOVE_TAB, o -> state.getTabManager().removeSelectedTab() );
		win.add( menu );
		win.add( new JSeparator() );

		JMenu windows = menu = new JMenu( ApplicationConstants.MB_WINDOWS );
		UIUtils.setColors( menu );
		createWindowItem( OutputLogConstants.WD_OUTPUT_LOG );
		menu.add( new JSeparator() );
		createWindowItem( ApplicationConstants.WD_CONTROL_PANEL );
		menu.add( new JSeparator() );
		createWindowItem( SSHConstants.WD_SSH );

		menu = new JMenu( ApplicationConstants.MB_SYS );
		UIUtils.setColors( menu );
		createWindowItem( ApplicationConstants.WD_SYS_INFO );
		windows.add( menu );

		JMenu cpu = menu = new JMenu( ApplicationConstants.MB_CPU );
		UIUtils.setColors( menu );
		createWindowItem( CPUConstants.WD_CPU_METERS );
		windows.add( menu );
		menu.add( new JSeparator() );
		menu = new JMenu( ApplicationConstants.MB_CHARTS );
		UIUtils.setColors( menu );
		createWindowItem( CPUConstants.WD_CPU_USAGE );
		createWindowItem( CPUConstants.WD_CPU_COMBINED_TEMP );
		createWindowItem( CPUConstants.WD_CPU_IND_TEMP );
		cpu.add( menu );

		JMenu mb = menu = new JMenu( ApplicationConstants.MB_DISK );
		UIUtils.setColors( menu );
		createWindowItem( DiskConstants.WD_DISK_METERS );
		createWindowItem( DiskConstants.WD_SCRUB_CONTROL );
		menu.add( new JSeparator() );
		createWindowItem( DiskConstants.WD_DISK_INFO_TABLE );
		createWindowItem( DiskConstants.WD_DISK_SCRUB_TABLE );
		windows.add( menu );
		menu.add( new JSeparator() );
		menu = new JMenu( ApplicationConstants.MB_CHARTS );
		UIUtils.setColors( menu );
		createWindowItem( DiskConstants.WD_DISK_COMBINED_TEMP );
		createWindowItem( DiskConstants.WD_DISK_IND_TEMP );
		mb.add( menu );

		menu = new JMenu( ApplicationConstants.MB_MEM );
		UIUtils.setColors( menu );
		createWindowItem( MemConstants.WD_MEM_METERS );
		windows.add( menu );

		JMenu net = menu = new JMenu( ApplicationConstants.MB_NET );
		UIUtils.setColors( menu );
		windows.add( menu );
		menu = new JMenu( ApplicationConstants.MB_CHARTS );
		UIUtils.setColors( menu );
		createWindowItem( NetConstants.WD_NET_BYTES );
		createWindowItem( NetConstants.WD_NET_PACKETS );
		net.add( menu );

		win.add( windows );

		menu = win;
		this.add( menu );
	}

	//	private void createLogMenu() {
	//		menu = new JMenu( ApplicationConstants.MB_LOGS );
	//		UIUtils.setColors( menu );
	//		createItem( ApplicationConstants.MB_LOGS_WARNING, o -> state.getWarningDialog().setVisible( true ) );
	//		this.add( menu );
	//	}

	private void createWindowItem( String command ) {
		createItem( command, o -> state.getTabManager().instantiateWindow( command, null ) );
	}

	@Override
	public void createBaseItem( GenericMenuBarAction action ) {
		item.addActionListener( new GenericActionListener( action, this ) );
		UIUtils.setColors( item );
		menu.add( item );
		buttonMap.put( item.getText(), item );
	}

	@Override
	public void update( Observable o, Object arg ) {
		boolean c = (Boolean)arg;
		if ( c ) {
			enabled( buttonMap.get( ApplicationConstants.MB_SSH_CONNECT ), false );
			enabled( buttonMap.get( ApplicationConstants.MB_SSH_DISCONNECT ), true );
		} else {
			enabled( buttonMap.get( ApplicationConstants.MB_SSH_CONNECT ), true );
			enabled( buttonMap.get( ApplicationConstants.MB_SSH_DISCONNECT ), false );
		}
	}

	private void enabled( JMenuItem item, boolean b ) {
		if ( b ) {
			UIUtils.setColors( item );
		} else { //if you don't do this, the items stay theme colored, even when disabled which is stupid...
			item.setForeground( null );
			item.setBackground( null );
		}
		item.setEnabled( b );
	}
}