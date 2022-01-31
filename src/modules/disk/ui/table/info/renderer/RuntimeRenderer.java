package modules.disk.ui.table.info.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import fnmcore.constants.ApplicationConstants;
import modules.disk.state.data.RuntimeData;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:20:38 PM 
 */
public class RuntimeRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;
	
	public RuntimeRenderer( ApplicationProvider state ) {
		super( state );
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		String s = (String)value;
		RuntimeData d = SmartUtils.getRuntimeData( s );
		( (JLabel)c ).setText( d.toString() );
		Color back;
		Color fore;
		if ( !isSelected ) {
			back = ThemeConstants.BACKGROUND;
			if ( d.years < 1 ) {
				fore = SILVER;
			} else if ( d.years < 2 ) {
				fore = BLUE;
			} else if ( d.years < 3 ) {
				fore = GREEN;
			} else if ( d.years < 4 ) {
				fore = YELLOW;
			} else if ( d.years < 5 ) {
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