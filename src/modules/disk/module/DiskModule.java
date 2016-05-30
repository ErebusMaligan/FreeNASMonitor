package modules.disk.module;

import java.util.Arrays;
import java.util.List;

import modules.disk.state.data.DiskData;
import modules.disk.state.data.RealtimeDiskData;
import modules.disk.state.monitor.DiskMonitor;
import modules.disk.state.monitor.RealtimeDiskMonitor;
import modules.disk.ui.window.definitions.DiskCombinedTempChartDefinition;
import modules.disk.ui.window.definitions.DiskIndividualTempChartDefinition;
import modules.disk.ui.window.definitions.DiskInfoTableDefinition;
import modules.disk.ui.window.definitions.DiskMeterDefinition;
import modules.disk.ui.window.definitions.DiskScrubControlDefinition;
import modules.disk.ui.window.definitions.DiskScrubTableDefinition;
import fnmcore.module.FNMModule;
import fnmcore.module.spi.SPIDataMonitorProvider;
import fnmcore.module.spi.SPIMonitorDataProvider;
import fnmcore.module.spi.SPIRealTimeMonitorProvider;
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
 * Created: May 1, 2016, 6:28:14 PM 
 */
public class DiskModule extends FNMModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIRealTimeMonitorProvider, SPIWindowDefinitionProvider {
	
	public static final String DISK_DATA = DiskData.class.getName();
	
	public static final String RT_DISK_DATA = RealtimeDiskData.class.getName();

	public static final String DISK_MONITOR = DiskMonitor.class.getName();

	public static final String RT_DISK_MONITOR = RealtimeDiskMonitor.class.getName();

	private DiskData dd;
	
	private RealtimeDiskData rdd;
	
	private DiskMonitor dm;
	
	private RealtimeDiskMonitor rdm;
	
	
	@Override
	public void init() {
		dd = new DiskData();
		rdd = new RealtimeDiskData();
	}

	@Override
	public void shutdown() {}

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new DiskScrubTableDefinition() );
		WindowManager.addWindowDefinition( new DiskInfoTableDefinition() );
		WindowManager.addWindowDefinition( new DiskMeterDefinition() );
		WindowManager.addWindowDefinition( new DiskScrubControlDefinition() );
		WindowManager.addWindowDefinition( new DiskCombinedTempChartDefinition() );
		WindowManager.addWindowDefinition( new DiskIndividualTempChartDefinition() );
		
	}

	@Override
	public void initRTDataMonitors( ApplicationState state, SSHSession ssh ) {
		rdm = new RealtimeDiskMonitor( state, rdd, ssh );
	}

	@Override
	public List<AbstractMonitor> getRTDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { rdm } );
	}

	@Override
	public List<MonitorData> getMonitorData() {
		return Arrays.asList( new MonitorData[] { dd, rdd } );
	}

	@Override
	public void initDataMonitors( ApplicationState state, SSHSession ssh ) {
		dm = new DiskMonitor( state, dd, ssh );
	}

	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { dm } );
	}

}
