package fnmcore.util;

import modules.disk.state.data.RuntimeData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 5:10:34 PM 
 */
public class SmartUtils {

	private static final double HOURS_PER_DAY = 24d;
	
	private static final double DAYS_PER_YEAR = 365d;  //i don't care about leap years
	
	public static RuntimeData getRuntimeData( String runTime ) {
		Double time = Double.parseDouble( runTime );
		Double days = Math.floor( time / HOURS_PER_DAY );
		Double years = Math.floor( days / DAYS_PER_YEAR );
		Double hours = time;
		if ( days != 0 ) {
			hours = time - ( days * HOURS_PER_DAY );
			days = days - ( years * DAYS_PER_YEAR );
		}
		return new RuntimeData( years.intValue(), days.intValue(), hours.intValue() );
	}
}
