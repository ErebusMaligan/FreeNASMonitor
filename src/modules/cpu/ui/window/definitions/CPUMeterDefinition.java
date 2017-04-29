package modules.cpu.ui.window.definitions;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import modules.cpu.module.CPUConstants;
import modules.cpu.ui.panel.charts.SimpleCPUChartPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 10:44:59 PM 
 */
public class CPUMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new SimpleCPUChartPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return CPUConstants.WD_CPU_METERS;
	}
}