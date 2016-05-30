package modules.disk.ui.window.definitions;

import javax.swing.JComponent;

import modules.disk.ui.panel.charts.AllDiskTemperatureChartPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class DiskCombinedTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllDiskTemperatureChartPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_DISK_COMBINED_TEMP;
	}
}