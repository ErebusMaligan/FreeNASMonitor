package modules.system.module;

import modules.system.ui.window.definitions.SystemInfoDefinition;
import fnmcore.module.FNMModule;
import fnmcore.module.spi.SPIWindowDefinitionProvider;
import gui.windowmanager.WindowManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:07:42 PM 
 */
public class SysModule extends FNMModule implements SPIWindowDefinitionProvider {

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new SystemInfoDefinition() );
	}

	@Override
	public void init() {}

	@Override
	public void shutdown() {}

}