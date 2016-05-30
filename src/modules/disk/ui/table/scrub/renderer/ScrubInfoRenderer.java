package modules.disk.ui.table.scrub.renderer;

import java.awt.Component;

import javax.swing.JTable;

import modules.disk.ui.table.info.renderer.SmartInfoRenderer;
import fnmcore.constants.AC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class ScrubInfoRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;

	protected static final int PROGRESS_COLUMN = 3;

	protected static final int TIME_COLUMN = 5;

	protected static final int REPAIRED_COLUMN = 6;

	protected static final int ERROR_COLUMN = 7;

	protected static final String CANCELED = "Canceled";

	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		Object time = table.getModel().getValueAt( row, TIME_COLUMN );
		Object error = table.getModel().getValueAt( row, ERROR_COLUMN );
		Object progress = table.getModel().getValueAt( row, PROGRESS_COLUMN );
		Object repair = table.getModel().getValueAt( row, REPAIRED_COLUMN );
		if ( !isSelected ) {
			boolean foundError = false;
			if ( error != null ) {
				int e = Integer.parseInt( (String)error );
				if ( e > 0 ) {
					if ( !isSelected ) {
						c.setBackground( AC.FOREGROUND );
						c.setForeground( AC.BACKGROUND );
					}
					foundError = true;
				}
			}

			if ( !foundError ) {
				boolean foundRepair = false;
				if ( repair != null ) {
					double r = Double.parseDouble( (String)repair );
					if ( r > 0d ) {
						if ( !isSelected ) {
							c.setBackground( YELLOW );
							c.setForeground( AC.BACKGROUND );
						}
						foundRepair = true;
					}
				}

				if ( !foundRepair ) {
					if ( time != null && time.equals( CANCELED ) ) {
						if ( !isSelected ) {
							c.setBackground( AC.BACKGROUND );
							c.setForeground( YELLOW );
						}
					} else if ( progress != null && Boolean.parseBoolean( (String)progress ) ) {
						c.setBackground( AC.BACKGROUND );
						c.setForeground( GREEN );
					} else {
						if ( !isSelected ) {
							c.setBackground( AC.BACKGROUND );
							c.setForeground( AC.FOREGROUND );
						}
					}

				}

			}
		}
		return c;
	}
}