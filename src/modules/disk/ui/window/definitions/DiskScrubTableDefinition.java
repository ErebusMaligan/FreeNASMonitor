package modules.disk.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.windowmanager.WindowDefinition;
import modules.disk.module.DiskConstants;
import modules.disk.ui.table.scrub.ScrubTable;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;
import ui.window.ClosableWindowPanel;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 5:57:30 AM 
 */
public class DiskScrubTableDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		ScrubTable scrubTable = new ScrubTable( (ApplicationProvider)state );
		JPanel scrub = new ClosableWindowPanel() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void closed() {
				scrubTable.shutdown();
			}
		};
		JScrollPane scroll2 = UIUtils.setJScrollPane( new JScrollPane( scrubTable.getTable() ) );
		scroll2.getViewport().setBackground( ThemeConstants.BACKGROUND );
		scrub.setLayout( new BorderLayout() );
		scrub.add( scroll2, BorderLayout.CENTER );
		return scrub;
	}

	@Override
	public String getTitle() {
		return DiskConstants.WD_DISK_SCRUB_TABLE;
	}
}