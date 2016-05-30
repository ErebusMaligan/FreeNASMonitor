package fnmcore.ui.window.definition;

import javax.swing.JComponent;

import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.panel.OutputLogPanel;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:15:36 PM 
 */
public class OutputLogDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new OutputLogPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_OUTPUT_LOG;
	}
}