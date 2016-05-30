package modules.net.state.net.monitor;

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
public class NetworkMonitor extends AbstractMonitor {
	
	public NetworkMonitor( ApplicationState state, MonitorData lh, SSHSession ssh ) {
		super( state, lh, ssh, AC.NET_INTERVAL );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( AC.NET_INFO_CMD, AC.NET_INFO_CMD ) );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {}
}