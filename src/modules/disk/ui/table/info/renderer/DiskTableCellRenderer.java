package modules.disk.ui.table.info.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fnmcore.constants.ApplicationConstants;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class DiskTableCellRenderer extends DefaultTableCellRenderer implements BroadcastListener {

	private static final long serialVersionUID = 1L;
	
	protected boolean lightsOff = false;
	
	public DiskTableCellRenderer( ApplicationProvider state ) {
		state.addBroadcastListener( this );
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( isSelected ) {
			c.setForeground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND );
			c.setBackground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND_DARKER, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND_DARKER );
		}
		if ( c instanceof JComponent ) {
			JComponent j = (JComponent)c;
			if ( isSelected ) {	
				j.setBorder( BorderFactory.createDashedBorder( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND  ) );
			} else {
				j.setBorder( null );
			}
		}
		return c;
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				lightsOff = false;
			} else {
				lightsOff = true;
			}
		}
	}
}