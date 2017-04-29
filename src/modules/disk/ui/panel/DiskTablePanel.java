package modules.disk.ui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import modules.disk.ui.table.info.DiskTable;
import modules.disk.ui.table.scrub.ScrubTable;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:13:47 AM 
 */
public class DiskTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public DiskTablePanel( ApplicationProvider state ) {
		DiskTable diskTable = new DiskTable( state );
		JPanel smart = new JPanel();
		JScrollPane scroll = UIUtils.setJScrollPane( new JScrollPane( diskTable.getTable() ) );
		scroll.getViewport().setBackground( ThemeConstants.BACKGROUND );
		smart.setLayout( new BorderLayout() );
		smart.add( scroll, BorderLayout.CENTER );
		
		ScrubTable scrubTable = new ScrubTable( state );
		JPanel scrub = new JPanel();
		JScrollPane scroll2 = UIUtils.setJScrollPane( new JScrollPane( scrubTable.getTable() ) );
		scroll2.getViewport().setBackground( ThemeConstants.BACKGROUND );
		scrub.setLayout( new BorderLayout() );
		scrub.add( scroll2, BorderLayout.CENTER );
		
		this.setLayout( new BorderLayout() );
		JSplitPane split = new JSplitPane( JSplitPane.VERTICAL_SPLIT, smart, scrub );
		this.add( split, BorderLayout.CENTER );
		split.setOneTouchExpandable( true );
		split.setDividerLocation( 400 );		
	}	
}