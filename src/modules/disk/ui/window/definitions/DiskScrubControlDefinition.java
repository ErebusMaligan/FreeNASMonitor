package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.disk.ui.panel.DiskScrubControlPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 6:27:13 AM 
 */
public class DiskScrubControlDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new DiskScrubControlPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_SCRUB_CONTROL;
	}
}