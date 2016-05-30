package modules.cpu.ui.panel.charts;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import statics.UIUtils;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import gui.layout.WrapLayout;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 7:35:46 PM 
 */
public class SimpleCPUChartPanel extends JPanel implements Observer, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private JPanel center = new JPanel();
	
	private ApplicationState state;
	
	private boolean init = false;
	
	private List<BroadcastListener> l = new ArrayList<>();
	
	public SimpleCPUChartPanel( ApplicationState state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
//		center.setLayout( new GridLayout( 0, 6 ) );
//		center.setLayout( new GridLayout( 3, 0 ) );
		center.setLayout( new WrapLayout() );
//		this.add( new JScrollPane( center ), BorderLayout.NORTH );
		this.add( center, BorderLayout.CENTER );
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
		state.getUIManager().addBroadcastListener( this );
		UIUtils.setColors( this, center );
	}

	@Override
	public void update( Observable o, Object arg ) {
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