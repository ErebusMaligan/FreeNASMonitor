package fnmcore.ui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import statics.UIUtils;
import fnmcore.state.ApplicationState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 7:34:52 AM 
 */
public class AllSSHSessionsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public AllSSHSessionsPanel( ApplicationState state ) {
		this.setLayout( new BorderLayout() );
		JTabbedPane tab = new JTabbedPane();
		UIUtils.setColors( this );
		UIUtils.setTabUI( tab );
		UIUtils.setColors( tab );
		state.getSSHManager().getSSHSessions().forEach( s -> tab.add( s.getProcessName(), new SSHSessionPanel( state, s.getProcessName() ) ) );
		this.add( tab, BorderLayout.CENTER );
	}
}