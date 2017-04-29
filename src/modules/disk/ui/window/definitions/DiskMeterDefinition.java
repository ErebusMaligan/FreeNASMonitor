package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import modules.disk.module.DiskConstants;
import modules.disk.ui.panel.charts.DiskIOPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 6:27:13 AM 
 */
public class DiskMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new DiskIOPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return DiskConstants.WD_DISK_METERS;
	}
}