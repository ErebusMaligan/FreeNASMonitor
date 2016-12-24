package modules.disk.ui.table.info.renderer;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 3:54:06 PM 
 */
public class DiskTableCellRenderer extends DefaultTableCellRenderer implements BroadcastListener {

	private static final long serialVersionUID = 1L;
	
	protected boolean lightsOff = false;
	
	public DiskTableCellRenderer( ApplicationState state ) {
		state.getUIManager().addBroadcastListener( this );
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
		Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
		if ( isSelected ) {
			c.setForeground( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND, AC.LIGHTS_OFF ) : AC.FOREGROUND );
			c.setBackground( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND_DARKER, AC.LIGHTS_OFF ) : AC.FOREGROUND_DARKER );
		}
		if ( c instanceof JComponent ) {
			JComponent j = (JComponent)c;
			if ( isSelected ) {	
				j.setBorder( BorderFactory.createDashedBorder( lightsOff ? UIUtils.lightsOff( AC.FOREGROUND, AC.LIGHTS_OFF ) : AC.FOREGROUND  ) );
			} else {
				j.setBorder( null );
			}
		}
		return c;
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.EVENT_TYPE.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.EVENT_SETTING.ON ) {
				lightsOff = false;
			} else {
				lightsOff = true;
			}
		}
	}
}