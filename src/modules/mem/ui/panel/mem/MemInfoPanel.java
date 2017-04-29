package modules.mem.ui.panel.mem;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modules.mem.module.MemModule;
import modules.mem.state.mem.data.MemData;
import modules.mem.ui.panel.mem.charts.SimpleMemoryChart;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 29, 2015, 8:28:37 PM 
 */
public class MemInfoPanel extends JPanel implements Observer, BroadcastListener {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationProvider state;
	
	private boolean init = false;
	
	private List<BroadcastListener> l = new ArrayList<>();
	
	public MemInfoPanel( ApplicationProvider state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
		UIUtils.setColors( this );
		state.getMonitorManager().getMonitorByName( MemModule.MEM_MONITOR ).addObserver( this );
		state.addBroadcastListener( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		if ( !init ) {
			init = true;
			PhysicalMemInfoPanel p = new PhysicalMemInfoPanel( ( (MemData)state.getMonitorManager().getDataByName( MemModule.MEM_DATA ) ).getPhysicalData() );
			this.add( p, BorderLayout.NORTH );
			TotalMemInfoPanel t = new TotalMemInfoPanel( ( (MemData)state.getMonitorManager().getDataByName( MemModule.MEM_DATA ) ) );
			this.add( t, BorderLayout.SOUTH );
			SimpleMemoryChart c = new SimpleMemoryChart( state );
			this.add( c, BorderLayout.CENTER );
			l.addAll( Arrays.asList( new BroadcastListener[] { p, t, c } ) );
		}
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		l.forEach( c -> c.broadcastReceived( e ) );
	}
}