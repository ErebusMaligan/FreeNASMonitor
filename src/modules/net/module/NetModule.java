package modules.net.module;

import java.util.Arrays;
import java.util.List;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIWindowDefinitionProvider;
import modules.net.state.net.data.NetworkData;
import modules.net.state.net.monitor.NetworkMonitor;
import modules.net.ui.window.definitions.NetBytesChartDefinition;
import modules.net.ui.window.definitions.NetPacketsChartDefinition;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:42:08 PM 
 */
public class NetModule extends AppModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIWindowDefinitionProvider {
	
	public static final String NET_DATA = NetworkData.class.getName();
	
	public static final String NET_MONITOR = NetworkMonitor.class.getName();
	
	private NetworkData data;
	
	private NetworkMonitor monitor;
	
	@Override
	public void init() {
		data = new NetworkData();
	}

	@Override
	public void shutdown() {}
	
	@Override
	public List<MonitorData> getMonitorData() {
		return Arrays.asList( new MonitorData[] { data } );
	}

	@Override
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		monitor = new NetworkMonitor( manager, broadcast, data, ssh );
	}

	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { monitor } );
	}

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new NetBytesChartDefinition() );
		WindowManager.addWindowDefinition( new NetPacketsChartDefinition() );
	}
}