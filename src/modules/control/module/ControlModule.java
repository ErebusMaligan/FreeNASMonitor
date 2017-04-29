package modules.control.module;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIWindowDefinitionProvider;
import modules.control.ui.window.definitions.ControlPanelDefinition;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:10:51 PM 
 */
public class ControlModule extends AppModule implements SPIWindowDefinitionProvider {

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new ControlPanelDefinition() );
	}

	@Override
	public void init() {}

	@Override
	public void shutdown() {}

}