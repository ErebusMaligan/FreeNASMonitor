package modules.disk.module;

import java.util.Arrays;
import java.util.List;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIRealTimeMonitorProvider;
import module.spi.SPIWindowDefinitionProvider;
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
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 6:28:14 PM 
 */
public class DiskModule extends AppModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIRealTimeMonitorProvider, SPIWindowDefinitionProvider {
	
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
	public void initRTDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		rdm = new RealtimeDiskMonitor( manager, broadcast, rdd, ssh );
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
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		dm = new DiskMonitor( manager, broadcast, dd, ssh );
	}

	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { dm } );
	}

}
