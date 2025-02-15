package modules.cpu.ui.panel.charts;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import gui.layout.WrapLayout;
import listeners.BasicObservable;
import listeners.BasicObserver;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
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
public class SimpleCPUChartPanel extends JPanel implements BasicObserver, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private JPanel center = new JPanel();
	
	private ApplicationProvider state;
	
	private boolean init = false;
	
	private List<BroadcastListener> l = new ArrayList<>();
	
	public SimpleCPUChartPanel( ApplicationProvider state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
//		center.setLayout( new GridLayout( 0, 6 ) );
//		center.setLayout( new GridLayout( 3, 0 ) );
		center.setLayout( new WrapLayout() );
//		this.add( new JScrollPane( center ), BorderLayout.NORTH );
		this.add( center, BorderLayout.CENTER );
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
		state.addBroadcastListener( this );
		UIUtils.setColors( this, center );
	}

	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( !init ) {
			for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
				SimpleCPUChart c = new SimpleCPUChart( i, state );
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