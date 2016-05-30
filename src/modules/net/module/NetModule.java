package modules.net.module;

import java.util.Arrays;
import java.util.List;

import modules.net.state.net.data.NetworkData;
import modules.net.state.net.monitor.NetworkMonitor;
import modules.net.ui.window.definitions.NetBytesChartDefinition;
import modules.net.ui.window.definitions.NetPacketsChartDefinition;
import fnmcore.module.FNMModule;
import fnmcore.module.spi.SPIDataMonitorProvider;
import fnmcore.module.spi.SPIMonitorDataProvider;
import fnmcore.module.spi.SPIWindowDefinitionProvider;
import fnmcore.state.ApplicationState;
import fnmcore.state.monitor.AbstractMonitor;
import fnmcore.state.monitor.MonitorData;
import fnmcore.state.ssh.SSHSession;
import gui.windowmanager.WindowManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:42:08 PM 
 */
public class NetModule extends FNMModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIWindowDefinitionProvider {
	
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
	public void initDataMonitors( ApplicationState state, SSHSession ssh ) {
		monitor = new NetworkMonitor( state, data, ssh );
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