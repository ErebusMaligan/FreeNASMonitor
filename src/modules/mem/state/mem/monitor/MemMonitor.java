package modules.mem.state.mem.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.mem.module.MemConstants;
import ssh.SSHSession;
import state.control.BroadcastManager;
import state.monitor.AbstractMonitor;
import state.monitor.MonitorData;
import state.monitor.MonitorManager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 10:35:42 PM 
 */
public class MemMonitor extends AbstractMonitor {

	public MemMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.CPU_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( MemConstants.MEM_PHYS_CMD, MemConstants.MEM_PHYS_CMD ) );
		handleCDL( createAction( MemConstants.MEM_OVERALL_CMD, MemConstants.MEM_OVERALL_CMD ) );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( MemConstants.MEM_CURRENT_CMD, MemConstants.MEM_CURRENT_CMD ) );
	}
}