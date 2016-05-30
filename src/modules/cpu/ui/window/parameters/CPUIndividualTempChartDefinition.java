package modules.cpu.ui.window.parameters;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import modules.cpu.ui.panel.charts.IndividualCPUTemperatureChartPanel;
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
public class CPUIndividualTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return UIUtils.setJScrollPane( new JScrollPane( new IndividualCPUTemperatureChartPanel( (ApplicationState)state ) ) );
	}

	@Override
	public String getTitle() {
		return AC.WD_CPU_IND_TEMP;
	}
}