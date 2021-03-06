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
public class TestResultRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;
	
	
	public TestResultRenderer( ApplicationProvider state ) {
		super( state );
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( !isSelected ) {
			String s = (String)value;
			Color fore;
			Color back;
			if ( s.equals( "PASSED" ) ) {
				back = ThemeConstants.BACKGROUND;
				fore = GREEN;
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
