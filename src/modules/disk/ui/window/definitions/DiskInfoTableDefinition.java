package modules.disk.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modules.disk.ui.table.info.DiskTable;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.panel.ClosableWindowPanel;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 5:14:16 AM 
 */
public class DiskInfoTableDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		DiskTable diskTable = new DiskTable( (ApplicationState)state );
		JPanel smart = new ClosableWindowPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void closed() {
				diskTable.shutdown();
			}
		};
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( diskTable.getTable() ) );
		scroll.getViewport().setBackground( AC.BACKGROUND );
		smart.setLayout( new BorderLayout() );
		smart.add( scroll, BorderLayout.CENTER );
		return smart;
	}

	@Override
	public String getTitle() {
		return AC.WD_DISK_INFO_TABLE;
	}
}