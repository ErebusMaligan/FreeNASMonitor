package modules.sensor.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.windowmanager.WindowDefinition;
import modules.sensor.module.SensorConstants;
import modules.sensor.ui.panel.meters.SimpleFanMeterPanel;
import state.provider.ApplicationProvider;
import statics.UIUtils;

public class FanMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		JPanel panel = new JPanel( new BorderLayout() );
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( new SimpleFanMeterPanel( (ApplicationProvider)state ) ) );
		panel.add( scroll, BorderLayout.CENTER );
		return panel;
	}

	@Override
	public String getTitle() {
		return SensorConstants.WD_FAN_METERS;
	}
}