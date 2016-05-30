package fnmcore.state.window;

import statics.UIUtils;
import fnmcore.constants.AC;
import gui.windowmanager.ComponentWindow;
import gui.windowmanager.WindowDefinition;
import gui.windowmanager.WindowManager;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 1:51:26 AM 
 */
public class FNMWindow extends ComponentWindow {
	
	private static final long serialVersionUID = 1L;
	
	public FNMWindow( Object state, WindowManager wm, WindowDefinition def ) {
		super( state, wm, def );
	}

	@Override
	public void additionalConfiguration( Object state ) {
		setFrameIcon( AC.APP_ICON );
		UIUtils.setColors( this );
	}
}