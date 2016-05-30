package modules.disk.ui.window.definitions;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import modules.disk.ui.panel.charts.IndividualDiskTemperatureChartPanel;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class DiskIndividualTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return UIUtils.setJScrollPane( new JScrollPane( new IndividualDiskTemperatureChartPanel( (ApplicationState)state ) ) );
	}

	@Override
	public String getTitle() {
		return AC.WD_DISK_IND_TEMP;
	}
}