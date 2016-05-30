package modules.mem.ui.window.definitions;

import javax.swing.JComponent;

import modules.mem.ui.panel.mem.MemInfoPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowDefinition;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 12:52:00 AM 
 */
public class MemoryMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new MemInfoPanel( (ApplicationState)state );
	}

	@Override
	public String getTitle() {
		return AC.WD_MEM_METERS;
	}
}