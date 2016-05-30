package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import modules.disk.ui.panel.DiskScrubControlPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 6:27:13 AM 
 */
public class DiskScrubControlDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new DiskScrubControlPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_SCRUB_CONTROL;
	}
}