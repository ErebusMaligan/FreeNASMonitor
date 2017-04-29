package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.disk.ui.panel.charts.AllDiskTemperatureChartPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class DiskCombinedTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllDiskTemperatureChartPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_DISK_COMBINED_TEMP;
	}
}