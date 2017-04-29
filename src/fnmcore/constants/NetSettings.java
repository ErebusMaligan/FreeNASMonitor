package fnmcore.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modules.net.module.NetConstants;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:04:55 AM 
 */
public class NetSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		ApplicationConstants.NI_NAME = e.get( NetConstants.XINTERFACE );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( ApplicationConstants.XNET, values );
		values.put( NetConstants.XINTERFACE, new String[] { ApplicationConstants.NI_NAME } );
		return ret;
	}
}