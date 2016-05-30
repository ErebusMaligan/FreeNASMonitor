package modules.disk.ui.table.info.renderer;

import java.awt.Component;

import javax.swing.JTable;

import fnmcore.constants.AC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:20:38 PM 
 */
public class TemperatureRenderer extends SmartInfoRenderer {
	
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( !isSelected ) {
			String s = (String)value;
			int i = Integer.parseInt( s );
			c.setBackground( AC.BACKGROUND );
			if ( i <= 35 ) {
				c.setForeground( BLUE );
			} else if ( i <= 39 ) {
				c.setForeground( GREEN );
			} else if ( i <= 45 ) {
				c.setForeground( YELLOW );
			} else if ( i <= 49 ) {
				c.setForeground( ORANGE );
			} else {
				c.setBackground( AC.FOREGROUND );
				c.setForeground( AC.BACKGROUND );
			}
		}
		return c;
	}
}