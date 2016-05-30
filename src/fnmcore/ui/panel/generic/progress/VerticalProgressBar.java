package fnmcore.ui.panel.generic.progress;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 3:53:09 AM 
 */
public class VerticalProgressBar extends JProgressBar {

	private static final long serialVersionUID = 1L;

	protected JLabel label = new JLabel();
	
	protected String unit;
	
	public VerticalProgressBar( int min, int max, String unit ) {
		super( SwingConstants.VERTICAL, min, max );
		this.unit = unit;
		this.setLayout( new BorderLayout() );
		label.setHorizontalAlignment( JLabel.CENTER );
		this.add( label, BorderLayout.CENTER );
		label.setOpaque( false );
	}
	
	@Override
	public void setValue( int n ) {
		super.setValue( n );
		label.setText( n + " " + unit );
		Color f = this.getForeground();
		Color b = this.getBackground();
		if ( n > this.getMaximum() / 2) {
			label.setForeground( b );
			label.setBackground( f );
		} else {
			label.setForeground( f );
			label.setBackground( b );
		}
	}

}
