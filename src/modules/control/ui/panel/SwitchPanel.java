package modules.control.ui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.button.ToggleButton;
import gui.label.ToggleLabel;
import icon.creator.SlitLightRockerSwitchIconCreator;
import icon.creator.TextIconCreator;
import statics.ColorUtils;
import statics.GU;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 7, 2015, 12:19:54 AM 
 */
public class SwitchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ToggleButton tb;
	
	private ToggleLabel tl;
	
	public SwitchPanel( String title, ActionListener l, boolean on ) {
		Icon[] i = new SlitLightRockerSwitchIconCreator().getIcons( ColorUtils.alignColor( ThemeConstants.BACKGROUND, 10, 200 ), ThemeConstants.FOREGROUND, 35, 70 );
		Icon[] j = new TextIconCreator().getIcons( title, new JLabel().getFont().deriveFont( 12f ), ThemeConstants.FOREGROUND, ThemeConstants.BACKGROUND );
		tb = new ToggleButton( i[ 0 ], i[ 1 ], on );
		tl = new ToggleLabel();
		tl.setToggles( j[ 0 ], j[ 1 ] );
		if ( on ) {
			tl.setOn();
		} else {
			tl.setOff();
		}
		tb.addActionListener( l );
		tb.addActionListener( e -> {
			if ( tb.isSelected() ) { 
				tl.setOn(); 
			} else { 
				tl.setOff(); 
			}
		} );
		this.setLayout( new BorderLayout() );
		this.add( tb, BorderLayout.NORTH );
		JPanel s = new JPanel();
		s.setLayout( new BoxLayout( s, BoxLayout.X_AXIS ) );
		s.add( tl );
		GU.hGlue2( s );
		this.add( s, BorderLayout.SOUTH );
		UIUtils.setColors( this, s );
	}
	
	public void setState( boolean state ) {
		if ( state ) {
			tl.setOn();
		} else {
			tl.setOff();
		}
		tb.setState( state );
	}
	
	public ToggleButton getButton() {
		return tb;
	}
	
	public ToggleLabel getLabel() {
		return tl;
	}
}