package modules.sensor.state.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.sensor.module.SensorConstants;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

public class SensorMonitor extends AbstractMonitor {
	
	public SensorMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.CPU_INTERVAL );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( SensorConstants.SENSOR_LIST_CMD, SensorConstants.SENSOR_LIST_CMD ) );
	}

	@Override
	protected void runOnce() throws InterruptedException {}
}