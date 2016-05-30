package modules.net.ui.window.definitions;

import javax.swing.JComponent;

import modules.net.ui.panel.net.charts.NetDoubleChartPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 9:36:42 AM 
 */
public class NetBytesChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new NetDoubleChartPanel( (ApplicationState)state, "Mbit Monitor", "Mbits./sec.", new String[] { AC.NET_IN_BYTES, AC.NET_OUT_BYTES } );
	}

	@Override
	public String getTitle() {
		return AC.WD_NET_BYTES;
	}
}