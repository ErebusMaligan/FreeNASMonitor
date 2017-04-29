package modules.disk.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.windowmanager.WindowDefinition;
import modules.disk.module.DiskConstants;
import modules.disk.ui.table.info.DiskTable;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;
import ui.window.ClosableWindowPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 5:14:16 AM 
 */
public class DiskInfoTableDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		DiskTable diskTable = new DiskTable( (ApplicationProvider)state );
		JPanel smart = new ClosableWindowPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void closed() {
				diskTable.shutdown();
			}
		};
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( diskTable.getTable() ) );
		scroll.getViewport().setBackground( ThemeConstants.BACKGROUND );
		smart.setLayout( new BorderLayout() );
		smart.add( scroll, BorderLayout.CENTER );
		return smart;
	}

	@Override
	public String getTitle() {
		return DiskConstants.WD_DISK_INFO_TABLE;
	}
}