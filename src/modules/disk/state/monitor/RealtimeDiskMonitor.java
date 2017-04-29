package modules.disk.state.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.disk.module.DiskConstants;
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
public class RealtimeDiskMonitor extends AbstractMonitor {
	
	public RealtimeDiskMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.REAL_TIME_DISK_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( DiskConstants.DISK_IO_CMD, DiskConstants.DISK_IO_CMD ) );
	}
}