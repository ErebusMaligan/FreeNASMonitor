package modules.mem.ui.window.definitions;

import javax.swing.JComponent;

import gui.windowmanager.WindowDefinition;
import modules.mem.module.MemConstants;
import modules.mem.ui.panel.mem.MemInfoPanel;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 12:52:00 AM 
 */
public class MemoryMeterDefinition implements WindowDefinition {

	@Override
	public JComponent getCenterComponent( Object state ) {
		return new MemInfoPanel( (ApplicationProvider)state );
	}

	@Override
	public String getTitle() {
		return MemConstants.WD_MEM_METERS;
	}
}