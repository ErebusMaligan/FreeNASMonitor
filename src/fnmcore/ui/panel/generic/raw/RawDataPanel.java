package fnmcore.ui.panel.generic.raw;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.ChartData;
import data.DataPair;
import data.DataSet;
import data.DataSetListener;
import statics.GU;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 1, 2015, 5:45:03 AM 
 */
public class RawDataPanel extends JPanel implements DataSetListener {

	private static final long serialVersionUID = 1L;

	protected ChartData cd;
	
	protected Map<String, DataPairPanel> map = new HashMap<>();
	
	protected JPanel center = new JPanel();
	
	protected JLabel time = new JLabel();
	
	public RawDataPanel( ChartData cd ) {
		this.cd = cd;
		cd.addListener( this );
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		time.setHorizontalAlignment( JLabel.CENTER );
		JPanel cent = GU.hp( this, null, GU.FIELD, time );
		this.add( center );
		GridLayout g = new GridLayout( 1, 0 );
		g.setVgap( 5 );
		center.setLayout( g );
//		this.setBorder( BorderFactory.createLineBorder( Color.DARK_GRAY ) );
		UIUtils.setColors( this, center, time, cent );
	}
	
	@Override
	public void dataAdded( String name, DataPair<?, ?> pair ) {
		time.setText( pair.getFirst().toString() );
		map.get( name ).setData( pair );
	}

	@Override
	public void dataSetAdded( DataSet<?, ?> set ) {
		DataPairPanel d = new DataPairPanel( set.getName() );
		map.put( d.getName(), d );
		center.add( d );
	}

	@Override
	public void dataCleared( String arg0 ) {} //this won't happen in this program
	
	@Override
	public void dataSetRemoved( String arg0 ) {} //neither will this
	
	
	private class DataPairPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		protected JLabel l = new JLabel();
		
		public DataPairPanel( String name ) {
			setName( name );
			this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
			l.setHorizontalAlignment( JLabel.CENTER );
			this.add( Box.createGlue() );
			JPanel info = GU.createVPanel( this, null, new Dimension( 100, 24 ), l );
			this.add( Box.createGlue() );
			UIUtils.setColors( this, info, l );
		}
		
		public void setData( DataPair<?, ?> pair ) {
			l.setText( "<html>" + getName() + ": <br>" + pair.getSecond().toString() + "<html>" );
			
		}
	}
}