package modules.disk.state.monitor;

import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
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
public class DiskMonitor extends AbstractMonitor {
	
	public DiskMonitor( ApplicationState state, MonitorData lh, SSHSession ssh ) {
		super( state, lh, ssh, AC.DISK_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( AC.DISK_LIST_CMD, AC.DISK_LIST_CMD ) );
		handleCDL( createAction( AC.MAP_GPTID_TO_DEVICE_CMD, AC.MAP_GPTID_TO_DEVICE_CMD ) );
		handleCDL( createAction( AC.POOL_LIST_CMD, AC.POOL_LIST_CMD ) );
		for ( String s : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getPools() ) {
			( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).setCurrentDisk( s );
			handleCDL( createAction( AC.POOL_STATUS_CMD, AC.POOL_STATUS_CMD + " " + s ) );
		}
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( AC.DISK_USAGE, AC.DISK_USAGE ) );
		if ( !kill ) {
			for ( String s : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
				( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).setCurrentDisk( s );
				if ( kill ) { break; };
				handleCDL( createAction( AC.DISK_SMART_INFO_CMD, AC.DISK_SMART_INFO_CMD + " " + s + AC.DISK_SMART_INFO_CMD_POST ) );
			}
		}
		if ( !kill ) {
			handleCDL( createAction( AC.DISK_SCRUB_STATUS, AC.DISK_SCRUB_STATUS ) );
			for ( ScrubInfo s : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getScrubs().values() ) {
				s.mapValues();
			}
		}
	}
}