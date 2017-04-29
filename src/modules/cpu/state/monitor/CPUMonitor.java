package modules.cpu.state.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.cpu.module.CPUConstants;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 11:17:02 PM 
 */
public class CPUMonitor extends AbstractMonitor {
	
	public CPUMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.CPU_INTERVAL );
	}

	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( CPUConstants.CPU_INFO_CMD, CPUConstants.CPU_INFO_CMD ) );
		handleCDL( createAction( CPUConstants.CPU_SENSOR_INFO_CMD, CPUConstants.CPU_SENSOR_INFO_CMD ) );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( CPUConstants.UPTIME_CMD, CPUConstants.UPTIME_CMD ) );
		handleCDL( createAction( CPUConstants.SYSTIME_CMD, CPUConstants.SYSTIME_CMD ) );
		handleCDL( createAction( CPUConstants.CPU_TEMP_CMD, CPUConstants.CPU_TEMP_CMD ) );
		handleCDL( createAction( CPUConstants.CPU_USAGE_CMD, CPUConstants.CPU_USAGE_CMD ) );
	}
}