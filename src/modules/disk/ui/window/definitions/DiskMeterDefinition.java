package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import modules.disk.ui.panel.charts.DiskIOPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 6:27:13 AM 
 */
public class DiskMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new DiskIOPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_DISK_METERS;
	}
}