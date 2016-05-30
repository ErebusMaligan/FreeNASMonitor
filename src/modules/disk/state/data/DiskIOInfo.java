package modules.disk.state.data;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 8, 2015, 10:23:27 PM 
 */
public class DiskIOInfo {
	
	public String name;
	
	public float busy;
	
	
	public float wms;
	
	public float wkbps;
	
	public float ws;
	
	
	public float rms;
	
	public float rkbps;
	
	public float rs;
	
	
	
	public float ops;
	
	public float lq;
	
	public DiskIOInfo( String l ) {
		String[] p = l.replaceAll( " +", " " ).split( " " );
		try {
			lq = Float.parseFloat( p[ 0 ] );
			ops = Float.parseFloat( p[ 1 ] );
			
			rs = Float.parseFloat( p[ 2 ] );
			rkbps = Float.parseFloat( p[ 3 ] );
			rms = Float.parseFloat( p[ 4 ] );
		
			ws = Float.parseFloat( p[ 5 ] );
			wkbps = Float.parseFloat( p[ 6 ] );
			wms = Float.parseFloat( p[ 7 ] );
			
			busy = Float.parseFloat( p[ 8 ] );
			name = p[ 9 ];
		} catch ( Exception e ) {
			//do nothing
		}
	}	
}