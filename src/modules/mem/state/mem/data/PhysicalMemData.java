package modules.mem.state.mem.data;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 10:56:33 PM 
 */
public class PhysicalMemData {

	public String locator;
	
	public int size;
	
	public String type;
	
	public String speed;

	public void skimMessage( String line ) {
		String s = line.trim();
		String[] p = s.split( ":" );
		String k = p[ 0 ].trim();
		String v = p[ 1 ].trim();
		switch( k ) {
			case "Size": size = Integer.parseInt( v.split( " " )[ 0 ] ); break;
			case "Type": type = v; break;
			case "Bank Locator": locator = v; break;
			case "Speed": speed = v; break;
		}
	}
}