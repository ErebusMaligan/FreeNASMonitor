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
public class DebugSettings implements XMLValues {
	
	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AC.DEBUG = Boolean.parseBoolean( e.get( AC.XDEBUG ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( AC.XDEBUG, values );
		values.put( AC.XDEBUG, new String[] { String.valueOf( AC.DEBUG ) } );
		return ret;
	}
}