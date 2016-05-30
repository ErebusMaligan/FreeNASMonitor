package fnmcore.ui.window.definition;

import javax.swing.JComponent;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.panel.AllSSHSessionsPanel;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 7:29:08 AM 
 */
public class SSHSessionDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new AllSSHSessionsPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_SSH;
	}
}