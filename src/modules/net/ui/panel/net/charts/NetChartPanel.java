package modules.net.ui.panel.net.charts;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fnmcore.util.ChartUtils;
import modules.net.module.NetConstants;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 6:09:38 PM 
 */
public class NetChartPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs = new JTabbedPane();
	
	protected ApplicationProvider state;

	public NetChartPanel( ApplicationProvider state ) {
		this.state = state;
		UIUtils.setColors( this, tabs );
		UIUtils.setTabUI( tabs );
		ChartUtils.onceTimeFormat();
		this.setLayout( new BorderLayout() );
		tabs.add( "Packets", new NetDoubleChartPanel( state, "Packet Monitor", "Packets/sec.", new String[] { NetConstants.NET_IN_PACKET, NetConstants.NET_OUT_PACKET } ) );
		tabs.add( "Bytes", new NetDoubleChartPanel( state, "Mbit Monitor", "Mbits./sec.", new String[] { NetConstants.NET_IN_BYTES, NetConstants.NET_OUT_BYTES } ) );
		this.add( tabs, BorderLayout.CENTER );
	}
}