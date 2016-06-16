package modules.disk.state.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
public class DiskData extends MonitorData implements ProcessStreamSiphon {
	
	private List<String> locations = new ArrayList<>();
	
	private List<String> pools = new ArrayList<>();
	
	private Map<String, String> devMap = new HashMap<>();
	
	private Map<String, String> poolMap = new HashMap<>();
	
	private Map<String, SmartInfo> info = new HashMap<>();
	
	private Map<String, ScrubInfo> scrubs = new HashMap<>();
	
	private Map<String, DFData> usage = new HashMap<>();
	
	private List<String> smartInfo;
	
	private String currentDisk;
	
	private static final String LOC = "/dev/";
	
	public DiskData() {
		skimmers.put( AC.DISK_LIST_CMD, line -> {
			if ( line.startsWith( "<" ) ) {
				String sub = line.substring( line.indexOf( "(" ) + 1, line.indexOf( "," ) );
				if ( sub.contains( "pass" ) ) {  //disks used to list as (da0,pass0)... but after latest update of 9.3, they are (pass0,da0).   Note this may not even be remotely the same for someone with different hardware setup.
					sub = line.substring( line.indexOf( "," ) + 1, line.indexOf( ")" ) );
				}
				String d = LOC + sub;
				if ( !locations.contains( d ) ) {
//					System.out.println( d );
					locations.add( d );
				}
			}
		} );
		
		skimmers.put( AC.MAP_GPTID_TO_DEVICE_CMD, line -> {
			if ( line.startsWith( "gptid" ) ) {
				line = line.trim();
				String dev = LOC + line.substring( line.lastIndexOf( " " ) + 1, line.length() - 2 );
				String gid = line.substring( 0, line.indexOf( " " ) );
//				System.out.println( dev + " : " + gid );
				devMap.put( dev, gid );
			}
		} );
		
		skimmers.put( AC.POOL_LIST_CMD, line -> {
			line = line.trim();
			if ( line.contains( "%" ) ) {
				pools.add( line.substring( 0, line.indexOf( " " ) ) );
			}
		} );
		
		skimmers.put( AC.POOL_STATUS_CMD, line -> {
			line = line.trim();
			if ( line.startsWith( "gptid" ) ) {
				String gid = line.substring( 0, line.indexOf( " " ) );
				poolMap.put( gid, currentDisk );
			}
		} );
		
		skimmers.put( AC.DISK_SMART_INFO_CMD, line -> {
			if ( line.startsWith( "===" ) ) {
				smartInfo = new ArrayList<>();
			}
			if ( smartInfo != null ) {
				smartInfo.add( line );
			}
			if ( line.contains( "delay." ) ) {
				info.put( currentDisk, new SmartInfo( currentDisk, poolMap.get( devMap.get( currentDisk ) ), smartInfo ) );
			}
		} );
		
		skimmers.put( AC.DISK_SCRUB_STATUS, line -> {
			String l = line.trim();
			if ( l.startsWith(  "pool:" ) ) {
				setCurrentDisk( l.substring( l.indexOf( ":" ) + 2 ) );	
				if ( !scrubs.containsKey( currentDisk ) ) {
					scrubs.put( currentDisk, new ScrubInfo( currentDisk ) );
				}
			} else if ( l.startsWith( "scan" ) || l.contains( "scanned" ) || l.contains(  "repaired" ) || l.contains( "canceled" ) || l.contains( "none requested" ) || l.contains( "resilver" ) ) {	
				scrubs.get( currentDisk ).processLine( l );
			}
		} );
		
		skimmers.put( AC.DISK_USAGE, line -> {
			if ( line.contains( "%" ) ) {
				DFData d = new DFData();
				d.skimMessage( line );
				usage.put( d.name, d );
			}
		} );
		
		ProcessManager.getInstance().registerSiphon( AC.SSH_MASTER_PROCESS_NAME, this );
	}
	
	public List<String> getLocations() {
		return Collections.unmodifiableList( locations );
	}
	
	public List<String> getPools() {
		return Collections.unmodifiableList( pools );
	}
	
	public Map<String, ScrubInfo> getScrubs() {
		return Collections.unmodifiableMap( scrubs );
	}
	
	public DFData getDiskUsage( String name ) {
		return usage.get( name );
	}
	
	public SmartInfo getInfo( String location ) {
		return info.get( location );
	}
	
	public String getPool( String location ) {
		return poolMap.get( devMap.get( location ) );
	}
	
	public int getPoolCount() {
		return poolMap.size();
	}
	
	public void setCurrentDisk( String currentDisk) {
		this.currentDisk = currentDisk;
	}
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}
}