package modules.cpu.ui.window.parameters;

import javax.swing.JComponent;

import modules.cpu.ui.panel.charts.AllCPUTemperatureChartPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class CPUCombinedTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllCPUTemperatureChartPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_CPU_COMBINED_TEMP;
	}
}