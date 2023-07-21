package modules.sensor.ui.panel.meters;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import gui.layout.WrapLayout;
import modules.sensor.module.SensorModule;
import modules.sensor.state.data.SensorData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 7:35:46 PM 
 */
public class SimpleFanMeterPanel extends JPanel implements Observer, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private JPanel center = new JPanel();
	
	private ApplicationProvider state;
	
	private boolean init = false;
	
	private List<BroadcastListener> l = new ArrayList<>();
	
	public SimpleFanMeterPanel( ApplicationProvider state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
		center.setLayout( new WrapLayout() );
		this.add( center, BorderLayout.CENTER );
		state.getMonitorManager().getMonitorByName( SensorModule.SENSOR_MONITOR ).addObserver( this );
		state.addBroadcastListener( this );
		UIUtils.setColors( this, center );
	}

	@Override
	public void update( Observable o, Object arg ) {
		if ( !init ) {
			for ( String s : ( (SensorData)state.getMonitorManager().getDataByName( SensorModule.SENSOR_DATA ) ).getFanNames() ) {
				SimpleFanMeter c = new SimpleFanMeter( s, state );
				center.add( c );
				l.add( c );
			}
			init = true;
		}
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		l.forEach( c -> c.broadcastReceived( e ) );
	}
}