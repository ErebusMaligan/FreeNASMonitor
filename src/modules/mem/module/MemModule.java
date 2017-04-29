package modules.mem.module;

import java.util.Arrays;
import java.util.List;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIWindowDefinitionProvider;
import modules.mem.state.mem.data.MemData;
import modules.mem.state.mem.monitor.MemMonitor;
import modules.mem.ui.window.definitions.MemoryMeterDefinition;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 7:21:00 PM 
 */
public class MemModule extends AppModule implements SPIMonitorDataProvider, SPIDataMonitorProvider, SPIWindowDefinitionProvider {
	
	public static final String MEM_DATA = MemData.class.getName();
	
	public static final String MEM_MONITOR = MemMonitor.class.getName();
	
	private MemData data;
	
	private MemMonitor monitor;
	
	@Override
	public void init() {
		data = new MemData();
	}

	@Override
	public void shutdown() {}

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new MemoryMeterDefinition() );
	}

	@Override
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		monitor = new MemMonitor( manager, broadcast, data, ssh );
	}

	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { monitor } );
	}

	@Override
	public List<MonitorData> getMonitorData() {
		return Arrays.asList(  new MonitorData[] { data } );
	}
}