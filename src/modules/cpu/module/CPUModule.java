package modules.cpu.module;

import java.util.Arrays;
import java.util.List;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIWindowDefinitionProvider;
import modules.cpu.state.data.CPUData;
import modules.cpu.state.monitor.CPUMonitor;
import modules.cpu.ui.window.definitions.CPUMeterDefinition;
import modules.cpu.ui.window.parameters.CPUCombinedTempChartDefinition;
import modules.cpu.ui.window.parameters.CPUIndividualTempChartDefinition;
import modules.cpu.ui.window.parameters.CPUUsageChartDefinition;
import modules.cpu.ui.window.parameters.DefaultCPUFrameParameters;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 6:57:37 PM 
 */
public class CPUModule extends AppModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIWindowDefinitionProvider {

	public static final String CPU_DATA = CPUData.class.getName();
	
	public static final String CPU_MONITOR = CPUMonitor.class.getName();
	
	private CPUData data;
	
	private CPUMonitor monitor;
	
	@Override
	public void init() {
		data = new CPUData();
	}

	@Override
	public void shutdown() {}

	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinitionAndDefaults( new CPUMeterDefinition(), new DefaultCPUFrameParameters() );
		WindowManager.addWindowDefinition( new CPUUsageChartDefinition() );
		WindowManager.addWindowDefinition( new CPUIndividualTempChartDefinition() );
		WindowManager.addWindowDefinition( new CPUCombinedTempChartDefinition() );
	}

	@Override
	public List<MonitorData> getMonitorData() {
		return Arrays.asList( new MonitorData[] { data } );
	}

	@Override
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		monitor = new CPUMonitor( manager, broadcast, data, ssh );
	}

	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { monitor } );
	}
}