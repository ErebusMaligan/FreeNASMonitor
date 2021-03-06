package modules.disk.ui.table.info.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import fnmcore.constants.ApplicationConstants;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:20:38 PM 
 */
public class TemperatureRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;
	
	public TemperatureRenderer( ApplicationProvider state ) {
		super( state );
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( !isSelected ) {
			String s = (String)value;
			int i = Integer.parseInt( s );
			Color back = ThemeConstants.BACKGROUND;
			Color fore;
			if ( i <= 35 ) {
				fore = BLUE;
			} else if ( i <= 39 ) {
				fore = GREEN;
			} else if ( i <= 45 ) {
				fore = YELLOW;
			} else if ( i <= 49 ) {
				fore = ORANGE;
			} else {
				back = ThemeConstants.FOREGROUND;
				fore = ThemeConstants.BACKGROUND;
			}
			c.setBackground( lightsOff ? UIUtils.lightsOff( back, ApplicationConstants.LIGHTS_OFF ) : back );
			c.setForeground( lightsOff ? UIUtils.lightsOff( fore, ApplicationConstants.LIGHTS_OFF ) : fore);
		}
		return c;
	}
}