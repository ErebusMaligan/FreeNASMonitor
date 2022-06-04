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
		if ( !slashBeforeNumber( line ) ) {  //this eliminates any sub-share mount points
//			System.out.println( "LINE: " + line );
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
			String p = l.get( 1 ).substring( 0, l.get( 1 ).length() - 1 );
			if ( p.contains( "." ) ) {
				p = p.substring( 0, p.indexOf( "." ) );
			}
			percent = Integer.parseInt( p );
		}
	}
	
	private boolean slashBeforeNumber( String line ) {
		boolean ret = false;
		for ( int i = 0; i < line.length(); i++ ) {
			char c = line.charAt( i );
			if ( Character.isDigit( c ) ) {
				break;
			}
			if ( c == '/' ) {
					ret = true;
			}
		}
		return ret;
	}
}