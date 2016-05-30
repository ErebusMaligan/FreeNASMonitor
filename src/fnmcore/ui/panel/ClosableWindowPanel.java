package fnmcore.ui.panel;

import gui.windowmanager.WindowClosedHook;

import javax.swing.JPanel;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 9:05:01 AM 
 */
public abstract class ClosableWindowPanel extends JPanel implements WindowClosedHook {

	private static final long serialVersionUID = 1L;

	@Override
	public abstract void closed();

}