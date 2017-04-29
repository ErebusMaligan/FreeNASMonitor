package modules.cpu.ui.window.parameters;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import gui.windowmanager.WindowDefinition;
import modules.cpu.module.CPUConstants;
import modules.cpu.ui.panel.charts.IndividualCPUTemperatureChartPanel;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class CPUIndividualTempChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return UIUtils.setJScrollPane( new JScrollPane( new IndividualCPUTemperatureChartPanel( (ApplicationProvider)state ) ) );
	}

	@Override
	public String getTitle() {
		return CPUConstants.WD_CPU_IND_TEMP;
	}
}