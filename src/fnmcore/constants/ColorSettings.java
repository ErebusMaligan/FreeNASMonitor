package fnmcore.constants;

import java.util.List;
import java.util.Map;

import props.utils.ColorUtils;
import ui.theme.BasicColorSettings;
import ui.theme.ThemeConstants;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 1:15:04 AM 
 */
public class ColorSettings extends BasicColorSettings implements XMLValues {

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		super.loadParamsFromXMLValues( e );
		ApplicationConstants.READ_WARNING = ColorUtils.toColor( e.get( ApplicationConstants.XRW ) );
		ApplicationConstants.PROGRESS_BAR_MID = ColorUtils.toColor( e.get( ApplicationConstants.XPBM ) );
		ApplicationConstants.PROGRESS_BAR_END = ColorUtils.toColor( e.get( ApplicationConstants.XPBE ) );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = super.saveParamsAsXML();
		Map<String, String[]> values = ret.get( ThemeConstants.XCOLORS );
		values.put( ApplicationConstants.XRW, new String[] { ColorUtils.toHex( ApplicationConstants.READ_WARNING ) } );
		values.put( ApplicationConstants.XPBM, new String[] { ColorUtils.toHex( ApplicationConstants.PROGRESS_BAR_MID ) } );
		values.put( ApplicationConstants.XPBE, new String[] { ColorUtils.toHex( ApplicationConstants.PROGRESS_BAR_END ) } );
		return ret;
		
	}
}