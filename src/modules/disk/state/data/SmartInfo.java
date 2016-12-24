package modules.disk.state.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fnmcore.constants.AC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 1:43:04 AM 
 */
public class SmartInfo {

	public String pool;
	
	public String device;
	
	public String family;
	
	public String model;
	
	public String serial;
	
	public String result;
	
	public String runtime;
	
	public String temp;
	
	public String capacity;
	
	public static final String DEVICE = "Device";
	
	public static final String SERIAL = "Serial #";
	
	public static final String TEMP = "Temperature (" + AC.DEGREE + "C)";
	
	public static final String RUN = "Run Time";
	
	public static final String RESULT = "Test Result";
	
	public static final String FAMILY = "Family";
	
	public static final String MODEL = "Model";
	
	public static final String POOL = "Pool";
	
	public static final String COMBINED = "[Pool] Device";
	
	public static final String CAPACITY = "User Capacity (TB)";
	
	public static final String[] orderedNames = new String[] { COMBINED, SERIAL, CAPACITY, TEMP, RUN, RESULT, FAMILY, MODEL };
	
	private Map<String, String> mapping = new HashMap<>();
	
	public SmartInfo( String device, String pool, List<String> info ) {
		this.pool = pool;
		this.device = device;
		for ( String i : info ) {
//			System.err.println( i ); //debug - prints all smart info
			processLine( i );
		}
		info.clear();
		mapping.put( DEVICE, device );
		mapping.put( SERIAL, serial );
		mapping.put( TEMP, temp );
		mapping.put( RUN, runtime );
		mapping.put( RESULT, result );
		mapping.put( FAMILY, family );
		mapping.put( MODEL, model );
		mapping.put( CAPACITY, capacity );
		mapping.put( POOL, pool );
		mapping.put( COMBINED, "[" + pool + "]  " + device );
	}
	
	public String getValue( Object key ) {
		return mapping.get( key );
	}
	
	private void processLine( String line ) {
		if ( line.startsWith( "Model Family" ) ) {
			family = col( line, 1 );
		} else if ( line.startsWith( "Device Model" ) ) {
			model = col( line, 1 );
		} else if ( line.startsWith( "Serial Number" ) ) {
			serial = col( line, 1 );
		} else if ( line.startsWith( "SMART overall-health" ) ) {
			result = col( line, 2 );
		} else if ( line.contains( "Power_On_Hours" ) ) {
			runtime = table( line );
		} else if ( line.contains( "Temperature_Celsius" ) ) {
			temp = table( line );
		} else if ( line.startsWith( "User Capacity" ) ) {
			capacity = col( line, 1 );
			capacity = capacity.substring( capacity.indexOf( "[" ) + 1, capacity.lastIndexOf( " " ) );
		}
	}
	
	private String col( String line, int offset ) {
		return line.substring( line.indexOf( ":" ) + offset ).trim();
	}
	
	private String table( String line ) {
		if ( line.contains( "Min/Max" ) ) {
			line = line.substring( 0, line.lastIndexOf( "(" ) -1  ); //fixes an issue where one of my new drives reports (Min/Max 25/47) after the normal temperature value - maybe part of SMART that only some drives support?
		}
		return line.substring( line.lastIndexOf( " " ) ).trim();
	}
	
	@Override
	public String toString() {
		return family + " " + model + " " + serial + " " + result + " " + runtime + " " + temp;
	}
}