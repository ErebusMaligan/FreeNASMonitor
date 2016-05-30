package modules.mem.state.mem.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import process.ProcessManager;
import process.io.ProcessStreamSiphon;
import fnmcore.constants.AC;
import fnmcore.state.monitor.MonitorData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 10:23:36 PM 
 */
public class MemData extends MonitorData implements ProcessStreamSiphon {

	private List<PhysicalMemData> phys = new ArrayList<>();

	private PhysicalMemData pmd = null;
	
	private int avail;
	
	private int real;
	
	private CurrentMemData cur = new CurrentMemData();
	
	public MemData() {
		skimmers.put( AC.MEM_PHYS_CMD, line -> {
			if ( line.contains( "Size: " ) ) {
				pmd = new PhysicalMemData();
			} else if ( line.contains( "Speed: " ) ) {
				pmd.skimMessage( line );
				phys.add( pmd );
				pmd = null;
			}
			if ( pmd != null ) {
				pmd.skimMessage( line );
			}
		} );
		
		skimmers.put( AC.MEM_OVERALL_CMD, line -> {
			if ( line.contains( /*"real"*/ "realmem" ) ) {
				real = parseMem( line );
			} else if ( line.contains( /*"avail"*/ "usermem" ) ) {
				avail = parseMem( line );
			}
		} );
		
		skimmers.put( AC.MEM_CURRENT_CMD, line -> {
			if ( line.startsWith( "Mem:" ) ) {
				cur.skimMessage( line );
			}
		} );
		
		
		ProcessManager.getInstance().registerSiphon( AC.SSH_MASTER_PROCESS_NAME, this );
	}
	
	private int parseMem( String line ) {
		line = line.trim();
//		return Integer.parseInt( line.substring( line.lastIndexOf( "(" ) + 1, line.lastIndexOf( " " ) ) );  old way, stopped working in latest 9.3 Freenas update
		return (int)( Float.parseFloat( line.substring( line.lastIndexOf( ":" ) + 1 ) ) / 1024f /1024f );
	}
	
	public List<PhysicalMemData> getPhysicalData() {
		return Collections.unmodifiableList( phys );
	}
	
	public int getAvailableMemory() {
		return avail;
	}
	
	public int getRealMemory() {
		return real;
	}
	
	public CurrentMemData getCurrentMem() {
		return cur;
	}
	
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}
}