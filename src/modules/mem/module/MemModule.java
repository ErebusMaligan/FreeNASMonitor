package modules.mem.module;

import java.util.Arrays;
import java.util.List;

import modules.mem.state.mem.data.MemData;
import modules.mem.state.mem.monitor.MemMonitor;
import modules.mem.ui.window.definitions.MemoryMeterDefinition;
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
 * Created: May 1, 2016, 7:21:00 PM 
 */
public class MemModule extends FNMModule implements SPIMonitorDataProvider, SPIDataMonitorProvider, SPIWindowDefinitionProvider {
	
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
	public void initDataMonitors( ApplicationState state, SSHSession ssh ) {
		monitor = new MemMonitor( state, data, ssh );
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