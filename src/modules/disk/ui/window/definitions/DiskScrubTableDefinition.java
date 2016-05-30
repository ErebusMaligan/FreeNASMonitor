package modules.disk.ui.window.definitions;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modules.disk.ui.table.scrub.ScrubTable;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.panel.ClosableWindowPanel;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 12, 2015, 5:57:30 AM 
 */
public class DiskScrubTableDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		ScrubTable scrubTable = new ScrubTable( (ApplicationState)state );
		JPanel scrub = new ClosableWindowPanel() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void closed() {
				scrubTable.shutdown();
			}
		};
		JScrollPane scroll2 = UIUtils.setJScrollPane( new JScrollPane( scrubTable.getTable() ) );
		scroll2.getViewport().setBackground( AC.BACKGROUND );
		scrub.setLayout( new BorderLayout() );
		scrub.add( scroll2, BorderLayout.CENTER );
		return scrub;
	}

	@Override
	public String getTitle() {
		return AC.WD_DISK_SCRUB_TABLE;
	}
}