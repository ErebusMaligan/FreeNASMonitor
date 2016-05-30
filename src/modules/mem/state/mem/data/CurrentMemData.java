package modules.mem.state.mem.data;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 25, 2015, 10:56:33 PM 
 */
public class CurrentMemData {

	public int a = 0;
	
	public int i = 0;
	
	public int w = 0;
	
	public int c = 0;
	
	public int f = 0;
	
	public String au = "M";
	
	public String iu = "M";
	
	public String wu = "M";
	
	public String cu = "K";
	
	public String fu = "M";
	
	public String an = "Active";
	
	public String in = "Inactive";
	
	public String wn = "Wired";
	
	public String cn = "Cache";
	
	public String fn = "Free";

	public void skimMessage( String line ) {
		String s = line.trim();
		s = s.replaceAll( ",", " " );
		s = s.replaceAll( "  ", " " );
		String[] p = s.split( " " );
		int x = 1;
		a = getValue( p[ x ] );
		au = getUnit( p[ x ] );
		a = adjustToMegs( a, au );
		au = "M";
		an = p[ ++x ];
		i = getValue( p[ ++x ] );
		iu = getUnit( p[ x ] );
		i = adjustToMegs( i, iu );
		iu = "M";
		in = p[ ++x ];
		w = getValue( p[ ++x ] );
		wu = getUnit( p[ x ] );
		w = adjustToMegs( w, wu );
		wu = "M";
		wn = p[ ++x ];
		

		c = getValue( p[ ++x ] );
		cu = getUnit( p[ x ] );
		c = adjustToMegs( c, cu );
		cu = "M";
		cn = p[ ++x ];
		
		//apparently cached can just not exist sometimes - if that's the case, backtrack the index and write 0 for cache
		if ( !cn.contains( "Cache" ) ) {
			x = x - 2;
			c = 0;
			cn = "Cache";
		}
		
		
		f = getValue( p[ ++x ] );
		fu = getUnit( p[ x ] );
		f = adjustToMegs( f, fu );
		fu = "M";
		fn = p[ ++x ];
	}
	
	private int adjustToMegs( int in, String unit ) {
		return unit.equals( "K" ) ? in / 1024 : unit.equals( "G" ) ? in * 1024 : in;
	}
	
	private int getValue( String s ) {
		return Integer.parseInt( s.substring( 0, s.length() - 1 ) );
	}
	
	private String getUnit( String s ) {
		return s.substring( s.length() - 1 );
	}
}