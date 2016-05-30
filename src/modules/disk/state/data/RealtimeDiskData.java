package modules.disk.state.data;

import java.util.HashMap;
import java.util.Map;

import process.ProcessManager;
import process.io.ProcessStreamSiphon;
import fnmcore.constants.AC;
import fnmcore.state.monitor.MonitorData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 11:41:32 PM 
 */
public class RealtimeDiskData extends MonitorData implements ProcessStreamSiphon {
	
	private Map<String, DiskIOInfo> io = new HashMap<>();
	
	private static final String LOC = "/dev/";
	
	public RealtimeDiskData() {
		skimmers.put( AC.DISK_IO_CMD, line -> { 
			String l = line.trim();
			if ( !l.equals( "" ) ) {
				DiskIOInfo i = new DiskIOInfo( l );
				if ( i.name != null ) {
					io.put( LOC + i.name, i );
				}
			}
		} );
		
		ProcessManager.getInstance().registerSiphon( AC.SSH_RT_PROCESS_NAME, this );
	}
	
	public Map<String, DiskIOInfo> getIOInfo() {
		return io;
	}
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}
}