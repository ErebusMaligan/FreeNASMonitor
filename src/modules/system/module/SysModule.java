package modules.system.module;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIWindowDefinitionProvider;
import modules.system.ui.window.definitions.SystemInfoDefinition;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:07:42 PM 
 */
public class SysModule extends AppModule implements SPIWindowDefinitionProvider {

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new SystemInfoDefinition() );
	}

	@Override
	public void init() {}

	@Override
	public void shutdown() {}

}