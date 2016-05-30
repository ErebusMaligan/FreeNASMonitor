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
public class TestResultRenderer extends SmartInfoRenderer {
	
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( !isSelected ) {
			String s = (String)value;
			if ( s.equals( "PASSED" ) ) {
				c.setBackground( AC.BACKGROUND );
				c.setForeground( GREEN );
			} else {
				c.setBackground( AC.FOREGROUND );
				c.setForeground( AC.BACKGROUND );
			}
		}
		return c;
	}
}
