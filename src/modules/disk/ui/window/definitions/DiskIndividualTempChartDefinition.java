package modules.disk.ui.window.definitions;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.disk.ui.panel.charts.IndividualDiskTemperatureChartPanel;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class DiskIndividualTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return UIUtils.setJScrollPane( new JScrollPane( new IndividualDiskTemperatureChartPanel( (ApplicationProvider)state ) ) );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_DISK_IND_TEMP;
	}
}