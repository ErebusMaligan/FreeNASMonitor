package fnmcore.ui.menu;

import javax.swing.JButton;
import javax.swing.JPanel;

import fnmcore.constants.ApplicationConstants;
import state.provider.ApplicationProvider;
import ui.theme.BasicColorDialog;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 11:36:30 PM 
 */
public class ColorDialog extends BasicColorDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton rw;
	
	private JButton pm;
	
	private JButton pe;
	
	public ColorDialog( ApplicationProvider state ) {
		super( state );
	}
	
	protected void initButtons( JPanel c ) {
		super.initButtons( c );
		rw = new JButton();
		pm = new JButton();
		pe = new JButton();
		initButton( rw, ApplicationConstants.READ_WARNING, ApplicationConstants.SET_COLOR_RW, c );
		initButton( pm, ApplicationConstants.PROGRESS_BAR_MID, ApplicationConstants.SET_COLOR_PBM, c );
		initButton( pe, ApplicationConstants.PROGRESS_BAR_END, ApplicationConstants.SET_COLOR_PBE, c );
	}
	
	protected void saveParams() {
		super.saveParams();
		ApplicationConstants.READ_WARNING = rw.getBackground();
		ApplicationConstants.PROGRESS_BAR_END = pe.getBackground();
		ApplicationConstants.PROGRESS_BAR_MID = pm.getBackground();
	}
}