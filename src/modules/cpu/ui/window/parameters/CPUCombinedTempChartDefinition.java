package modules.cpu.ui.window.parameters;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.cpu.ui.panel.charts.AllCPUTemperatureChartPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class CPUCombinedTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllCPUTemperatureChartPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_CPU_COMBINED_TEMP;
	}
}