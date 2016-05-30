package modules.disk.state.monitor;

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
public class RealtimeDiskMonitor extends AbstractMonitor {
	
	public RealtimeDiskMonitor( ApplicationState state, MonitorData lh, SSHSession ssh ) {
		super( state, lh, ssh, AC.REAL_TIME_DISK_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( AC.DISK_IO_CMD, AC.DISK_IO_CMD ) );
	}
}