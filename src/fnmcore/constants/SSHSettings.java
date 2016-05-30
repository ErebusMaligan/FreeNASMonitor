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
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class SSHSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AC.SSH_USER = e.get( AC.XSSHU );
		AC.SSH_PW = e.get( AC.XSSHPW );
		AC.SSH_IP = e.get( AC.XSSHIP );
		AC.SSH_APP_PATH = e.get( AC.XPLINK );
		if ( e.get( AC.XSSHKEYPATH ) != null ) {
			AC.SSH_KEY_PATH = e.get( AC.XSSHKEYPATH );
		}
		if ( e.get( AC.XSSHUSEKEY ) != null ) {
			AC.SSH_USE_KEY = Boolean.parseBoolean( e.get( AC.XSSHUSEKEY ) );
		}
		if ( e.get( AC.XSSHPORT ) != null ) {
			AC.SSH_PORT = e.get( AC.XSSHPORT );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( AC.XSSH, values );
		values.put( AC.XSSHU, new String[] { AC.SSH_USER } );
		values.put( AC.XSSHPW, new String[] { AC.SSH_PW } );
		values.put( AC.XSSHIP, new String[] { AC.SSH_IP } );
		values.put( AC.XPLINK, new String[] { AC.SSH_APP_PATH } );
		values.put( AC.XSSHUSEKEY, new String[] { String.valueOf( AC.SSH_USE_KEY ) } );
		values.put( AC.XSSHKEYPATH, new String[] { AC.SSH_KEY_PATH } );
		values.put( AC.XSSHPORT, new String[] { AC.SSH_PORT } );
		return ret;
	}
}