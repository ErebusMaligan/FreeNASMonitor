package modules.sensor.module;

import java.util.Arrays;
import java.util.List;

import gui.windowmanager.WindowManager;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIMonitorDataProvider;
import module.spi.SPIWindowDefinitionProvider;
import modules.sensor.state.data.SensorData;
import modules.sensor.state.monitor.SensorMonitor;
import modules.sensor.ui.window.definitions.FanMeterDefinition;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

public class SensorModule extends AppModule implements SPIDataMonitorProvider, SPIMonitorDataProvider, SPIWindowDefinitionProvider {

	public static final String SENSOR_DATA = SensorData.class.getName();
	
	public static final String SENSOR_MONITOR = SensorMonitor.class.getName();
	
	private SensorData data;
	
	private SensorMonitor monitor;
	
	@Override
	public void init() {
		data = new SensorData();
	}

	@Override
	public void shutdown() {}
	
	@Override
	public void loadWindowDefinitions() {
		WindowManager.addWindowDefinition( new FanMeterDefinition() );
	}

	@Override
	public List<MonitorData> getMonitorData() {
		return Arrays.asList( new MonitorData[] { data } );
	}

	@Override
	public void initDataMonitors( MonitorManager manager, BroadcastManager broadcast, SSHSession ssh ) {
		monitor = new SensorMonitor( manager, broadcast, data, ssh );
	}
	
	@Override
	public List<AbstractMonitor> getDataMonitors() {
		return Arrays.asList( new AbstractMonitor[] { monitor } );
	}
}