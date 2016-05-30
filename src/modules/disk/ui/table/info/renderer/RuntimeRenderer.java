package modules.disk.ui.table.info.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import modules.disk.state.data.RuntimeData;
import fnmcore.constants.AC;
import fnmcore.util.SmartUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:20:38 PM 
 */
public class RuntimeRenderer extends SmartInfoRenderer {
	
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		String s = (String)value;
		RuntimeData d = SmartUtils.getRuntimeData( s );
		( (JLabel)c ).setText( d.toString() );
		if ( !isSelected ) {
			c.setBackground( AC.BACKGROUND );
			if ( d.years < 1 ) {
				c.setForeground( BLUE );
			} else if ( d.years < 2 ) {
				c.setForeground( GREEN );
			} else if ( d.years < 3 ) {
				c.setForeground( YELLOW );
			} else if ( d.years < 4 ) {
				c.setForeground( ORANGE );
			} else {
				c.setBackground( AC.FOREGROUND );
				c.setForeground( AC.BACKGROUND );
			}
		}
		return c;
	}
}