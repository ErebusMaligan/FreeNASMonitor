package fnmcore.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:08:24 AM 
 */
public class IntervalSettings implements XMLValues {
	
	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AC.DISK_INTERVAL = Long.parseLong( e.get( AC.XDISK ) );
		AC.CPU_INTERVAL = Long.parseLong( e.get( AC.XCPU ) );
		AC.NET_INTERVAL = Long.parseLong( e.get( AC.XNET ) );
		AC.REAL_TIME_DISK_INTERVAL = Long.parseLong( e.get( AC.XRT ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( AC.XINT, values );
		values.put( AC.XDISK, new String[] { String.valueOf( AC.DISK_INTERVAL ) } );
		values.put( AC.XCPU, new String[] { String.valueOf( AC.CPU_INTERVAL ) } );
		values.put( AC.XNET, new String[] { String.valueOf( AC.NET_INTERVAL ) } );
		values.put( AC.XRT, new String[] { String.valueOf(AC. REAL_TIME_DISK_INTERVAL ) } );
		return ret;
	}
}