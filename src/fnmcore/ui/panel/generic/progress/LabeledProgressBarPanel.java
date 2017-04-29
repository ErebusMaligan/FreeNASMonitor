package fnmcore.ui.panel.generic.progress;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import statics.GU;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 4:16:59 AM 
 */
public class LabeledProgressBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public LabeledProgressBarPanel( JProgressBar bar, String name, String units ) {
		this( bar, name, units, 40, 20 );
	}
	
	public LabeledProgressBarPanel( JProgressBar bar, String name, String units, Integer width, Integer height ) {
		JLabel n = getLabel( name + ": ", JLabel.RIGHT, new Dimension( width, height ) );
		JLabel v = getLabel( "", JLabel.RIGHT, new Dimension( width, height  ) );
		v.setBorder( BorderFactory.createLineBorder( ThemeConstants.FOREGROUND_DARKER ) );
		JLabel u = getLabel( " " + units, JLabel.LEFT, new Dimension( width, height ) );
		bar.addChangeListener( e -> { v.setText( "" + bar.getValue() ); } );
		UIUtils.setColors( this, n, v, u );
		if ( bar.getOrientation() == JProgressBar.HORIZONTAL ) {
			this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
			this.add( n );
			this.add( bar );
			this.add( v );
			this.add( u );
		} else {
			this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
			JPanel l = new JPanel ();
			l.setLayout( new BoxLayout( l, BoxLayout.X_AXIS ) );
			l.add( Box.createHorizontalGlue() );
			l.add( n );
			l.add( Box.createHorizontalGlue() );
			n.setHorizontalAlignment( JLabel.CENTER );
			GU.sizes( n, new Dimension( width * 2, height ) );
			this.add( bar );
			JPanel p = new JPanel();
			p.setLayout( new BoxLayout( p, BoxLayout.X_AXIS ) );
			UIUtils.setColors( p, l );
			p.add( v );
			p.add( u );
			this.add( l );
			this.add( p );
		}
	}
	
	private JLabel getLabel( String s, int align, Dimension dim ) {
		JLabel l = new JLabel( s );
		l.setHorizontalAlignment( align );
		GU.setSizes( l, dim );
		return l;
	}
}