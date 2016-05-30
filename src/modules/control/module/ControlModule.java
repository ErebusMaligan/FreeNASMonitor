package modules.control.module;

import modules.control.ui.window.definitions.ControlPanelDefinition;
import fnmcore.module.FNMModule;
import fnmcore.module.spi.SPIWindowDefinitionProvider;
import gui.windowmanager.WindowManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:10:51 PM 
 */
public class ControlModule extends FNMModule implements SPIWindowDefinitionProvider {

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new ControlPanelDefinition() );
	}

	@Override
	public void init() {}

	@Override
	public void shutdown() {}

}