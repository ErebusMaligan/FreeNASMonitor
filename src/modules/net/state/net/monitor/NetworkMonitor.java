package modules.net.state.net.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.net.module.NetConstants;
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
public class NetworkMonitor extends AbstractMonitor {
	
	public NetworkMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.NET_INTERVAL );
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( NetConstants.NET_INFO_CMD, NetConstants.NET_INFO_CMD ) );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {}
}