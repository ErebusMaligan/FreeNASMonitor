package modules.net.ui.window.definitions;

import javax.swing.JComponent;

import fnmcore.constants.ApplicationConstants;
import gui.windowmanager.WindowDefinition;
import modules.net.module.NetConstants;
import modules.net.ui.panel.net.charts.NetDoubleChartPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 9:36:42 AM 
 */
public class NetPacketsChartDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new NetDoubleChartPanel( (ApplicationProvider)state, "Packet Monitor", "Packets/sec.", new String[] { NetConstants.NET_IN_PACKET, NetConstants.NET_OUT_PACKET } );
	}

	@Override
	public String getTitle() {
		return ApplicationConstants.WD_NET_PACKETS;
	}
}