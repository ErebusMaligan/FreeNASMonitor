package modules.cpu.state.monitor;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.monitor.AbstractMonitor;
import fnmcore.state.monitor.MonitorData;
import fnmcore.state.ssh.SSHSession;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 11:17:02 PM 
 */
public class CPUMonitor extends AbstractMonitor {
	
	public CPUMonitor( ApplicationState state, MonitorData lh, SSHSession ssh ) {
		super( state, lh, ssh, AC.CPU_INTERVAL );
	}

	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( AC.CPU_INFO_CMD, AC.CPU_INFO_CMD ) );
		handleCDL( createAction( AC.CPU_SENSOR_INFO_CMD, AC.CPU_SENSOR_INFO_CMD ) );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( AC.UPTIME_CMD, AC.UPTIME_CMD ) );
		handleCDL( createAction( AC.SYSTIME_CMD, AC.SYSTIME_CMD ) );
		handleCDL( createAction( AC.CPU_TEMP_CMD, AC.CPU_TEMP_CMD ) );
		handleCDL( createAction( AC.CPU_USAGE_CMD, AC.CPU_USAGE_CMD ) );
	}
}