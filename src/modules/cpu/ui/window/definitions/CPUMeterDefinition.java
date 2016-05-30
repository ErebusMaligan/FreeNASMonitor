package modules.cpu.ui.window.definitions;

import javax.swing.JComponent;

import modules.cpu.ui.panel.charts.SimpleCPUChartPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 10:44:59 PM 
 */
public class CPUMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new SimpleCPUChartPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_CPU_METERS;
	}
}