package modules.disk.state.monitor;

import fnmcore.constants.ApplicationConstants;
import modules.disk.module.DiskConstants;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
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
public class DiskMonitor extends AbstractMonitor {
	
	public DiskMonitor( MonitorManager manager, BroadcastManager broadcast, MonitorData lh, SSHSession ssh ) {
		super( manager, broadcast, lh, ssh, ApplicationConstants.DISK_INTERVAL );
	}
	
	@Override
	protected void runOnce() throws InterruptedException {
		handleCDL( createAction( DiskConstants.DISK_LIST_CMD, DiskConstants.DISK_LIST_CMD ) );
		handleCDL( createAction( DiskConstants.MAP_GPTID_TO_DEVICE_CMD, DiskConstants.MAP_GPTID_TO_DEVICE_CMD ) );
		handleCDL( createAction( DiskConstants.POOL_LIST_CMD, DiskConstants.POOL_LIST_CMD ) );
		for ( String s : ( (DiskData)manager.getDataByName( DiskModule.DISK_DATA ) ).getPools() ) {
			( (DiskData)manager.getDataByName( DiskModule.DISK_DATA ) ).setCurrentDisk( s );
			handleCDL( createAction( DiskConstants.POOL_STATUS_CMD, DiskConstants.POOL_STATUS_CMD + " " + s ) );
		}
	}

	@Override
	protected void runLoop() throws InterruptedException {
		handleCDL( createAction( DiskConstants.DISK_USAGE, DiskConstants.DISK_USAGE ) );
		if ( !kill ) {
			for ( String s : ( (DiskData)manager.getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
				( (DiskData)manager.getDataByName( DiskModule.DISK_DATA ) ).setCurrentDisk( s );
				if ( kill ) { break; };
				handleCDL( createAction( DiskConstants.DISK_SMART_INFO_CMD, DiskConstants.DISK_SMART_INFO_CMD + " " + s + DiskConstants.DISK_SMART_INFO_CMD_POST ) );
			}
		}
		if ( !kill ) {
			handleCDL( createAction( DiskConstants.DISK_SCRUB_STATUS, DiskConstants.DISK_SCRUB_STATUS ) );
			for ( ScrubInfo s : ( (DiskData)manager.getDataByName( DiskModule.DISK_DATA ) ).getScrubs().values() ) {
				s.mapValues();
			}
		}
	}
}