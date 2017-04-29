package fnmcore.ui.panel.generic.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.progress.LabeledProgressBarPanel;
import gui.progress.DigitalJProgressBar;
import gui.progress.EnhancedJProgressBar;
import state.provider.ApplicationProvider;
import statics.GUIUtils;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 9:39:28 PM 
 */
public abstract class SimpleChart extends /*TransparentPanel*/ JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	protected ApplicationProvider state;
	
	protected Dimension dim = new Dimension( 125, 10 );
	
	protected int width = 40;
	
	protected int height = 20;
	
	protected JLabel n = new JLabel();
	
	protected JPanel center = new JPanel();

	public SimpleChart( ApplicationProvider state, Observable o ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
		this.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createLineBorder( ThemeConstants.FOREGROUND_DARKER ), BorderFactory.createBevelBorder( BevelBorder.RAISED ) ) );
		o.addObserver( this );
		center.setLayout( new BoxLayout( center, BoxLayout.Y_AXIS ) );
		this.add( center, BorderLayout.CENTER );
		UIUtils.setColors( this, center );
	}
	
	protected EnhancedJProgressBar addJProgressBar( EnhancedJProgressBar bar, int min, int max, String name, String units ) {
		if ( bar instanceof DigitalJProgressBar ) {	
			( (DigitalJProgressBar)bar ).setSegments( 10 );
		}
		if ( bar instanceof EnhancedJProgressBar ) {
			calculateSections( (EnhancedJProgressBar)bar );
		}
		GUIUtils.setSizes( bar, dim );
		UIUtils.setColors( bar );
		center.add( new LabeledProgressBarPanel( bar, name, units, width, height ) );
		bar.setValue( min + 1 );
		return bar;
	}
	
	protected EnhancedJProgressBar addJProgressBar( int min, int max, String name, String units ) {
		return addJProgressBar( new DigitalJProgressBar( min, max ), min, max, name, units );
	}
	
	protected void calculateSections( EnhancedJProgressBar bar ) {
		try {
			bar.setSectionTwo( ApplicationConstants.PROGRESS_BAR_MID, (int)( bar.getMaximum() * .65 ) );
			bar.setSectionThree( ApplicationConstants.PROGRESS_BAR_END, (int)( bar.getMaximum() * .9 ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	protected void setLabel( String name ) {
		n.setText( name );
		n.setHorizontalAlignment( JLabel.CENTER );
		JPanel south = new JPanel( new BorderLayout() );
		south.add( n, BorderLayout.CENTER );
		this.add( south, BorderLayout.SOUTH );
		UIUtils.setColors( south, n );
	}
}