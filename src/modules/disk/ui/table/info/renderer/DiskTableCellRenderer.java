package modules.disk.ui.table.info.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fnmcore.constants.AC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class DiskTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( isSelected ) {
			c.setForeground( AC.FOREGROUND );
			c.setBackground( AC.FOREGROUND_DARKER );
		}
		if ( c instanceof JComponent ) {
			JComponent j = (JComponent)c;
			if ( isSelected ) {	
				j.setBorder( BorderFactory.createDashedBorder( AC.FOREGROUND ) );
			} else {
				j.setBorder( null );
			}
		}
		return c;
	}
}