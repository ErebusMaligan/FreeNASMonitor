package fnmcore.ui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import statics.GU;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.button.CustomButtonUI;
import gui.dialog.OKCancelDialog;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 11:36:30 PM 
 */
public class ColorDialog extends OKCancelDialog {

	private static final long serialVersionUID = 1L;

	private ApplicationState state;
	
	private JButton bg = new JButton();
	
	private JButton fg = new JButton();
	
	private JButton rw = new JButton();
	
	private JButton pm = new JButton();
	
	private JButton pe = new JButton();
	
	public ColorDialog( ApplicationState state ) {
		super( state.getUIManager().getFrame(), AC.SET_COLOR_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );		

		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		initButton( bg, AC.BACKGROUND, AC.SET_COLOR_BG, c );
		initButton( fg, AC.FOREGROUND, AC.SET_COLOR_FG, c );
		initButton( rw, AC.READ_WARNING, AC.SET_COLOR_RW, c );
		initButton( pm, AC.PROGRESS_BAR_MID, AC.SET_COLOR_PBM, c );
		initButton( pe, AC.PROGRESS_BAR_END, AC.SET_COLOR_PBE, c );
		
		this.add( c, BorderLayout.CENTER );
		JPanel bp = getButtonPanel();
		this.add( bp, BorderLayout.SOUTH );
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		UIUtils.setColors( this, c, bp );
		this.pack();
	}
	
	private void initButton( JButton b, Color back, String label, JPanel panel ) {
//		b.setContentAreaFilled( false );
		b.setUI( new CustomButtonUI() );
		b.setOpaque( true );
		b.addActionListener( e -> {
			JButton button = ( (JButton)e.getSource() );
			Color c = JColorChooser.showDialog( ColorDialog.this, "Select New Color", button.getBackground() );
			if ( c != null ) {
				button.setBackground( c );
			}
		} );
		b.setBackground( back );
		b.setBorder( BorderFactory.createLineBorder( Color.DARK_GRAY ) );
		JLabel l = new JLabel( label );
		UIUtils.setColors( l );
		UIUtils.setColors( GU.hp( panel, l, b ) );
		GU.spacer( panel );
	}
	
	public void ok() {
		AC.BACKGROUND = bg.getBackground();
		AC.FOREGROUND = fg.getBackground();
		AC.READ_WARNING = rw.getBackground();
		AC.PROGRESS_BAR_END = pe.getBackground();
		AC.PROGRESS_BAR_MID = pm.getBackground();
		state.writeSettings();
		JOptionPane.showMessageDialog( this, "Color Options Require Application Restart To Take Effect" );
		super.ok();
	}
}