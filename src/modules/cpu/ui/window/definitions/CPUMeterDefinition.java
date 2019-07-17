package modules.cpu.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.windowmanager.WindowDefinition;
import modules.cpu.module.CPUConstants;
import modules.cpu.ui.panel.charts.SimpleCPUChartPanel;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 11, 2015, 10:44:59 PM 
 */
public class CPUMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		JPanel panel = new JPanel( new BorderLayout() );
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( new SimpleCPUChartPanel( (ApplicationProvider)state ) ) );
		panel.add( scroll, BorderLayout.CENTER );
		return panel;
	}

	@Override
	public String getTitle() {
		return CPUConstants.WD_CPU_METERS;
	}
}