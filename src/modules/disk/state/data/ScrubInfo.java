package modules.disk.state.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 4, 2015, 11:04:56 PM 
 */
public class ScrubInfo {
	
	public String pool;
	
	public double repaired;
	
	public int errors;
	
	public String time;
	
	public String dateComplete;
	
	public boolean inProgress;

	public float done  = 0;
	
	public String type = "";
	
	public static final String RESILVER = "Resilver";
	
	public static final String SCRUB = "Scrub";
	
	public static final String TYPE = "Type";
	
	public static final String POOL = "Pool";
	
	public static final String PROGRESS = "In Progress";
	
	public static final String DATE = "Last Run";
	
	public static final String COMPLETION = "Time to Completion";
	
	public static final String DONE = "% Done";
	
	public static final String REPAIRED = "Repaired";
	
	public static final String ERRORS = "Errors";
	
	public static final String[] orderedNames = new String[] { POOL, TYPE, DATE, PROGRESS, DONE, COMPLETION, REPAIRED, ERRORS };
	
	private Map<String, Object> mapping = new HashMap<>();
	
	public ScrubInfo( String pool ) {
		this.pool = pool;
	}
	
	public Object getValue( Object key ) {
		return mapping.get( key );
	}
	
	public void mapValues() {
		mapping.put( POOL, pool );
		mapping.put( TYPE, type );
		mapping.put( PROGRESS, inProgress );
		mapping.put( DONE, done );
		mapping.put( DATE, dateComplete );
		mapping.put( COMPLETION, time );
		mapping.put( REPAIRED, repaired );
		mapping.put( ERRORS, errors );
	}
	
	public void processLine( String l ) {	
//		System.out.println( l );
		String[] p = l.replaceAll( " +", " " ).split( " " );
		if ( l.startsWith( "scan" ) ) {
			if ( l.contains( "scrub" ) ) {
				type = SCRUB;
			}
			if ( !l.contains( "none requested" ) ) { //none available if a scan has never been done
				setTimeStamp( p );				
			}
			if ( l.contains( "repaired" ) ) {
				inProgress = false;
				//if it's 0 it just says 0, but if it's an amount, it gives a decimal and unit amount
				String amt = p[ 3 ];
				if ( !p[ 3 ].equals( "0" ) ) {
					amt = amt.substring( 0, amt.length() - 1 );
				}
				repaired = Double.parseDouble( amt );
				done = 100f;				
				if ( l.contains( "days" ) ) { //if the scrub took longer than 24 hours it adds " X days " before the time
					time = p[ 5 ] + " " + p[ 6 ] + " " + p[ 7 ];
					errors = Integer.parseInt( p[ 9 ] );
				} else {
					time = p[ 5 ];
					errors = Integer.parseInt( p[ 7 ] );
				}
			} else if ( l.contains( "canceled" ) ) {
				inProgress = false;
				repaired = -1;
				time = "Canceled";
				errors = -1;
				done = -1f;
			} else if ( l.contains( "resilvered" ) ) {  //this is for last thing run that completed is resilvered
				type = RESILVER;
				inProgress = false;
				String amt = p[ 2 ];
				if ( !p[ 2 ].equals( "0" ) ) {
					amt = amt.substring( 0, amt.length() - 1 );
				}
				repaired = Double.parseDouble( amt );
				time = p[ 4 ];
				errors = Integer.parseInt( p[ 8 ] );
				done = 100f;
			} else if ( l.contains( "resilver" ) ) {  //this if for resilver is currently in progress
				type = RESILVER;
				inProgress = true;
			} else {
				inProgress = true;
			}
		} else if ( l.contains( "scanned" ) ) {
			time = p[ p.length - 3 ];
		} else if ( l.contains( "done" ) ) {
			//if it's 0 it just says 0, but if it's an amount, it gives a decimal and unit amount
			String amt = p[ 0 ];
			if ( !p[ 0 ].equals( "0" ) ) {
				amt = amt.substring( 0, amt.length() - 1 );
			}
			repaired = Double.parseDouble( amt );
			
			errors = 0;
			String pDone = p[ 2 ];
			pDone = pDone.substring( 0, pDone.length() - 1 );
			time = p[ 4 ];
			done = Float.parseFloat( pDone );
		}
		//separate condition
		if ( l.contains( "none requested" ) ) {
			repaired = 0;
			errors = 0;
			done = 0;
			inProgress = false;
		}
	}
	
	private void setTimeStamp( String[] p ) {
		dateComplete = "";
		for ( int i = p.length - 1; i > p.length - 6; i-- ) {
			dateComplete = p[ i ] + " " + dateComplete;
		}
		dateComplete.trim();
	}
}