package modules.disk.state.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jun 7, 2015, 9:37:08 PM 
 */
public class DFData {
	
	public long used;
	
	public long available;
	
	public int percent;
	
	public String name  = "";
	
	public void skimMessage( String line ) {
		String[] s = line.trim().replaceAll( "\\s+", " " ).split( " " );
		List<String> l = Arrays.asList( s );
		Collections.reverse( l );
		for ( int i = 5; i < l.size(); i++ ) {
			if ( i != 5 ) {
				name += " ";
			}
			name += l.get( i );
		}
		used = Long.parseLong( l.get( 3 ) );
		available = Long.parseLong( l.get( 2 ) );
		percent = /*100l - */Integer.parseInt( l.get( 1 ).substring( 0, l.get( 1 ).length() - 1 ) );
	}
}