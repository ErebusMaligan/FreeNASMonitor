package modules.cpu.ui.window.parameters;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import modules.cpu.module.CPUConstants;
import modules.cpu.ui.panel.charts.AllCPUUsageChartPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 8:11:07 AM 
 */
public class CPUUsageChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllCPUUsageChartPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return CPUConstants.WD_CPU_USAGE;
	}
}