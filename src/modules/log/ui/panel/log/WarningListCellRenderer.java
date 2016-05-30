package modules.log.ui.panel.log;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import modules.log.state.log.LogEntry;
import statics.UIUtils;
import fnmcore.constants.AC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 3, 2015, 7:46:36 AM 
 */
public class WarningListCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent( JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
		JLabel c = (JLabel)super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
		LogEntry l = ( (LogEntry)value);
		c.setText( "[" + l.getTime() + "] - " + l.getMessage()  );
		if ( !isSelected ) {
			UIUtils.setColors( c );
		}
		if ( l.isRead() ) {
			c.setForeground( AC.READ_WARNING );
		}
		return c;
	}

}
