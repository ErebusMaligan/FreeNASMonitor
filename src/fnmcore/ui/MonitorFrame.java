package fnmcore.ui;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;

import fnmcore.ui.menu.MonitorMenuBar;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.window.AbstractApplicationFrame;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 7:48:08 PM 
 */
public class MonitorFrame extends AbstractApplicationFrame {

	private static final long serialVersionUID = 1L;
	
	private ApplicationProvider provider;
	
	public MonitorFrame( ApplicationProvider provider, GraphicsDevice d  ) {
		super( provider.getTabManager(), provider.getSSHManager(), provider.getMonitorManager(), provider, d );
		this.provider = provider;
		finishConstructor();
	}
	
	@Override
	public void construct() {
		setLayout( new BorderLayout() );
		add( provider.getTabManager().getTabPane(), BorderLayout.CENTER );
		add( new MonitorMenuBar( provider ), BorderLayout.NORTH );
	}

	@Override
	public void finishInit() {
		UIUtils.setColors( this.getContentPane() );		
		setVisible( true );
	} }