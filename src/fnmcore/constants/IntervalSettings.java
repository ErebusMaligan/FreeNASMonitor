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
		ApplicationConstants.DISK_INTERVAL = Long.parseLong( e.get( ApplicationConstants.XDISK ) );
		ApplicationConstants.CPU_INTERVAL = Long.parseLong( e.get( ApplicationConstants.XCPU ) );
		ApplicationConstants.NET_INTERVAL = Long.parseLong( e.get( ApplicationConstants.XNET ) );
		ApplicationConstants.REAL_TIME_DISK_INTERVAL = Long.parseLong( e.get( ApplicationConstants.XRT ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( ApplicationConstants.XINT, values );
		values.put( ApplicationConstants.XDISK, new String[] { String.valueOf( ApplicationConstants.DISK_INTERVAL ) } );
		values.put( ApplicationConstants.XCPU, new String[] { String.valueOf( ApplicationConstants.CPU_INTERVAL ) } );
		values.put( ApplicationConstants.XNET, new String[] { String.valueOf( ApplicationConstants.NET_INTERVAL ) } );
		values.put( ApplicationConstants.XRT, new String[] { String.valueOf(ApplicationConstants. REAL_TIME_DISK_INTERVAL ) } );
		return ret;
	}
}