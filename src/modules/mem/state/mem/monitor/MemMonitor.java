package modules.mem.state.mem.monitor;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.monitor.AbstractMonitor;
import fnmcore.state.monitor.MonitorData;
import fnmcore.state.ssh.SSHSession;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 10:35:42 PM 
 */
public class MemMonitor extends AbstractMonitor {

	public MemMonitor( ApplicationState state, MonitorData lh, SSHSession ssh ) {
		super( state, lh, ssh, AC.CPU_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( AC.MEM_PHYS_CMD, AC.MEM_PHYS_CMD ) );
		handleCDL( createAction( AC.MEM_OVERALL_CMD, AC.MEM_OVERALL_CMD ) );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( AC.MEM_CURRENT_CMD, AC.MEM_CURRENT_CMD ) );
	}
}