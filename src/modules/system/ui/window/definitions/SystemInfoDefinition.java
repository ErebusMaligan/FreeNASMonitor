package modules.system.ui.window.definitions;

import javax.swing.JComponent;

import modules.system.ui.panel.SystemInfoPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 6:43:08 AM 
 */
public class SystemInfoDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new SystemInfoPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_SYS_INFO;
	}
}