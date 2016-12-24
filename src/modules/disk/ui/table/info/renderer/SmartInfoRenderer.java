package modules.disk.ui.table.info.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class SmartInfoRenderer extends DiskTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	protected static final Color GREEN = new Color( 51, 204, 51 );
	
	protected static final Color YELLOW = new Color( 204, 204, 0 );
	
	protected static final Color ORANGE = new Color( 255, 153, 0 );
	
	protected static final Color BLUE = new Color( 51, 102, 200 );
	
	protected static final Color SILVER = new Color( 185, 185, 185 );
	
	public SmartInfoRenderer( ApplicationState state ) {
		super( state );
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( !isSelected ) {
			c.setBackground( lightsOff ? UIUtils.lightsOff( AC.BACKGROUND, AC.LIGHTS_OFF ) : AC.BACKGROUND );
			c.setForeground( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND, AC.LIGHTS_OFF ) : AC.FOREGROUND );
		}
		return c;
	}
}