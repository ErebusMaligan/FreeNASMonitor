package modules.disk.ui.table.info.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.util.SmartUtils;
import modules.disk.state.data.RuntimeData;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:20:38 PM 
 */
public class RuntimeRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;
	
	public RuntimeRenderer( ApplicationState state ) {
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
			back = AC.BACKGROUND;
			if ( d.years < 1 ) {
				fore = BLUE;
			} else if ( d.years < 2 ) {
				fore = GREEN;
			} else if ( d.years < 3 ) {
				fore = YELLOW;
			} else if ( d.years < 4 ) {
				fore = ORANGE;
			} else {
				back = AC.FOREGROUND;
				fore = AC.BACKGROUND;
			}
			c.setBackground( lightsOff ? UIUtils.lightsOff( back, AC.LIGHTS_OFF ) : back );
			c.setForeground( lightsOff ? UIUtils.lightsOff( fore, AC.LIGHTS_OFF ) : fore);
		}
		return c;
	}
}