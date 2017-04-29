package modules.control.ui.window.definitions;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.control.ui.panel.ApplicationControlPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 10:44:59 PM 
 */
public class ControlPanelDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new ApplicationControlPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_CONTROL_PANEL;
	}
}