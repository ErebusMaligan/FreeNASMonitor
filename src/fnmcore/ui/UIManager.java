package fnmcore.ui;

import java.awt.GraphicsDevice;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import modules.log.ui.panel.log.WarningDialog;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.ShutdownComponent;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.state.window.FNMTabManager;
import gui.textarea.DefaultJTextAreaStreamManager;
import gui.windowmanager.TabManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:53:24 PM 
 */
public class UIManager {
	
	private ApplicationState state;
	
	private MonitorFrame frame;
	
	private DefaultJTextAreaStreamManager output = new DefaultJTextAreaStreamManager();

	private WarningDialog wd;
	
	private List<ShutdownComponent> shutdown = new ArrayList<>();
	
	private Vector<WeakReference<BroadcastListener>> broadcastListeners = new Vector<>();
	
	private TabManager tab;
	
	public UIManager( ApplicationState state ) {
		tab = new FNMTabManager( state );
	}
	
	public DefaultJTextAreaStreamManager getOutput() {
		return output;
	}
	
	public void init( ApplicationState state, GraphicsDevice device ) {
		this.state = state;
		frame = new MonitorFrame( state, device );
		wd = new WarningDialog( state );
		tab.additionalConfiguration( state );  //if this isn't called here, the tabs don't get properly colored because it was called at construction before xml properties were loaded, so tabs have default colors
		tab.loadPreviousConfig();
	}
	
	public void registerShutdownComponent( ShutdownComponent c ) {
		shutdown.add( c );
	}
	
	public void shutdownComponents() {
		shutdown.forEach( c -> c.shutdown() );
	}
	
	public void saveWindowSettings() {
		AC.FRAME_H = frame.getHeight();
		AC.FRAME_W = frame.getWidth();
		AC.FRAME_X = frame.getLocation().x;
		AC.FRAME_Y = frame.getLocation().y;
		AC.FRAME_MAX_STATE = frame.getExtendedState();
		AC.FRAME_SCREEN = frame.getGraphicsConfiguration().getDevice().getIDstring();
		state.writeSettings();
	}
	
	public WarningDialog getWarningDialog() {
		return wd;
	}
	
	public MonitorFrame getFrame() {
		return frame;
	}
	
	public TabManager getTabManager() {
		return tab;
	}
	
	public void addBroadcastListener( BroadcastListener l ) {
		broadcastListeners.add( new WeakReference<BroadcastListener>( l ) );
	}
	
	public void broadcast( BroadcastEvent e ) {
		System.out.println( "Broadcasting: " + e );
		broadcastListeners.forEach( l -> l.get().broadcastReceived( e ) );
	}
}