package modules.system.ui.window.definitions;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.system.ui.panel.SystemInfoPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 6:43:08 AM 
 */
public class SystemInfoDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new SystemInfoPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_SYS_INFO;
	}
}