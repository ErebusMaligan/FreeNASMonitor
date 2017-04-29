package modules.disk.ui.table.scrub.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import fnmcore.constants.ApplicationConstants;
import modules.disk.state.data.ScrubInfo;
import modules.disk.ui.table.info.renderer.SmartInfoRenderer;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class ScrubInfoRenderer extends SmartInfoRenderer {

	private static final long serialVersionUID = 1L;

	protected static final int TYPE_COLUMN = 1;
	
	protected static final int PROGRESS_COLUMN = 3;

	protected static final int TIME_COLUMN = 5;

	protected static final int REPAIRED_COLUMN = 6;

	protected static final int ERROR_COLUMN = 7;

	protected static final String CANCELED = "Canceled";
	
	public ScrubInfoRenderer( ApplicationProvider state ) {
		super( state );
	}

	public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		Object type = table.getModel().getValueAt( row, TYPE_COLUMN );
		Object time = table.getModel().getValueAt( row, TIME_COLUMN );
		Object error = table.getModel().getValueAt( row, ERROR_COLUMN );
		Object progress = table.getModel().getValueAt( row, PROGRESS_COLUMN );
		Object repair = table.getModel().getValueAt( row, REPAIRED_COLUMN );
		
		Color fore = null;
		Color back = null;
		
		if ( !isSelected ) {
			boolean foundError = false;
			if ( error != null ) {						//error condition trumps everything, show that regardless - resilver causes repair, but should never cause error unless something is broken
				int e = Integer.parseInt( (String)error );
				if ( e > 0 ) {
					back = ThemeConstants.FOREGROUND;
					fore = ThemeConstants.BACKGROUND;
					foundError = true;
				}
			}

			if ( !foundError ) {				
				if ( type != null && type.equals( ScrubInfo.RESILVER ) && ( progress != null && Boolean.parseBoolean( (String)progress ) ) ) {  //specifically handle resilver in progress
					back = SILVER;
					fore = ThemeConstants.BACKGROUND;
				} else if ( type != null && type.equals( ScrubInfo.RESILVER ) && ( progress != null && !Boolean.parseBoolean( (String)progress ) ) ) { //specifically handle resilver last completed task for this drive
					back = BLUE;
					fore = ThemeConstants.BACKGROUND;
				} else {
					boolean foundRepair = false;
					if ( repair != null ) {
						double r = Double.parseDouble( (String)repair );
						if ( r > 0d ) {
							back = YELLOW;
							fore = ThemeConstants.BACKGROUND;
							foundRepair = true;
						}
					}
	
					if ( !foundRepair ) {
						if ( time != null && time.equals( CANCELED ) ) {
							back = ThemeConstants.BACKGROUND;
							fore = YELLOW;
						} else if ( progress != null && Boolean.parseBoolean( (String)progress ) ) {
							back = ThemeConstants.BACKGROUND;
							fore = GREEN;
						} else {
							back = ThemeConstants.BACKGROUND;
							fore = ThemeConstants.FOREGROUND;
						}
	
					}
				}
			}
			c.setBackground( lightsOff ? UIUtils.lightsOff( back, ApplicationConstants.LIGHTS_OFF ) : back );
			c.setForeground( lightsOff ? UIUtils.lightsOff( fore, ApplicationConstants.LIGHTS_OFF ) : fore);
		}
		return c;
	}
}