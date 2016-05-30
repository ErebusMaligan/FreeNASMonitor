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
public class WindowSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AC.FRAME_H = Integer.parseInt( e.get( AC.XWH ) );
		AC.FRAME_W = Integer.parseInt( e.get( AC.XWW ) );
		AC.FRAME_X = Integer.parseInt( e.get( AC.XWX ) );
		AC.FRAME_Y = Integer.parseInt( e.get( AC.XWY ) );
		AC.FRAME_MAX_STATE = Integer.parseInt( e.get( AC.XWM ) );
		AC.FRAME_SCREEN = e.get( AC.XWS );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( AC.XWINDOW, values );
		values.put( AC.XWH, new String[] { String.valueOf( AC.FRAME_H ) } );
		values.put( AC.XWW, new String[] { String.valueOf( AC.FRAME_W ) } );
		values.put( AC.XWX, new String[] { String.valueOf( AC.FRAME_X ) } );
		values.put( AC.XWY, new String[] { String.valueOf( AC.FRAME_Y ) } );
		values.put( AC.XWM, new String[] { String.valueOf( AC.FRAME_MAX_STATE ) } );
		values.put( AC.XWS, new String[] { AC.FRAME_SCREEN } );
		return ret;
	}
}