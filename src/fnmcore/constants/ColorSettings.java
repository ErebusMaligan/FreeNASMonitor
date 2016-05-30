package fnmcore.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import props.utils.ColorUtils;
import statics.UIUtils;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class ColorSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		AC.BACKGROUND = ColorUtils.toColor( e.get( AC.XBG ) );
		AC.FOREGROUND = ColorUtils.toColor( e.get( AC.XFG ) );
		UIUtils.FOREGROUND = AC.FOREGROUND;
		UIUtils.BACKGROUND = AC.BACKGROUND;
		AC.FOREGROUND_DARKER = AC.FOREGROUND.darker().darker().darker().darker();
		AC.READ_WARNING = ColorUtils.toColor( e.get( AC.XRW ) );
		AC.PROGRESS_BAR_MID = ColorUtils.toColor( e.get( AC.XPBM ) );
		AC.PROGRESS_BAR_END = ColorUtils.toColor( e.get( AC.XPBE ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<String, String[]>();
		ret.put( AC.XCOLORS, values );
		values.put( AC.XBG, new String[] { ColorUtils.toHex( AC.BACKGROUND ) } );
		values.put( AC.XFG, new String[] { ColorUtils.toHex( AC.FOREGROUND ) } );
		values.put( AC.XRW, new String[] { ColorUtils.toHex( AC.READ_WARNING ) } );
		values.put( AC.XPBM, new String[] { ColorUtils.toHex( AC.PROGRESS_BAR_MID ) } );
		values.put( AC.XPBE, new String[] { ColorUtils.toHex( AC.PROGRESS_BAR_END ) } );
		return ret;
	}
}