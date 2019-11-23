package modules.disk.ui.panel.charts;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import gui.layout.WrapLayout;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.DiskIOInfo;
import modules.disk.state.data.RealtimeDiskData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 12:39:47 AM 
 */
public class DiskIOPanel extends JPanel implements Observer, DiskIOChartHolder, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private Map<String, DiskIOChart> io = new HashMap<>();
	
	private ApplicationProvider state;
	
	private JPanel center = new JPanel();
	
	private int maxR = 0;
	
	private int maxW = 0;
	
	private int cc = 0;
	
	private List<String> added = new ArrayList<String>();
	
	private Map<String, Integer> updated = new HashMap<>();
	
	public DiskIOPanel( ApplicationProvider state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
//		center.setLayout( new GridLayout( 0, 6 ) );
		center.setLayout( new WrapLayout() );
//		this.add( new JScrollPane( center ), BorderLayout.NORTH );
		this.add( center, BorderLayout.NORTH );
		state.getMonitorManager().getMonitorByName( DiskModule.RT_DISK_MONITOR ).addObserver( this );
		UIUtils.setColors( this, center );
		state.addBroadcastListener( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		maxR = 0;
		maxW = 0;
		for ( String s : ( (RealtimeDiskData)state.getMonitorManager().getDataByName( DiskModule.RT_DISK_DATA ) ).getIOInfo().keySet() ) {
			synchronized ( io ) {
				if ( !added.contains( s ) ) {
					DiskIOChart chart = new DiskIOChart( this, s, state );
					added.add( s );
					io.put( s, chart );
					center.add( chart );
					cc++;
				}
			}
			DiskIOInfo i = ( (RealtimeDiskData)state.getMonitorManager().getDataByName( DiskModule.RT_DISK_DATA ) ).getIOInfo().get( s );
			int r = new Float( i.rkbps ).intValue();
			int w = new Float( i.wkbps ).intValue();
			if ( r > maxR ) {
				maxR = r;
			}
			if ( w > maxW ) {
				maxW = w;
			}
		}
		synchronized ( io ) {
			for ( DiskIOChart c : io.values() ) {
				c.updateMax( maxR, maxW );
			}
		}
	}

	@Override
	public void removeAndSort( DiskIOChart r, boolean add ) {
		center.remove( r );
		cc--;
		if ( r.getPool() != null ) {
			updated.computeIfAbsent( r.getPool(), k -> 0 );
			updated.put( r.getPool(), updated.get( r.getPool() ) + 1 );
		}
		io.remove( r.getNM() );
		if ( add ) {
			io.put( r.getFullName(), r );
		}
		if ( cc == 0 || evenPools() ) {
			sortAndAdd();
		}
	}
	
	private boolean evenPools() {
		boolean ret = true;
		if ( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getPoolCount() != 0 ) {
			List<String> pools = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getPools();
			for ( String s : pools ) {
				if ( !s.equals( "freenas-boot" ) ) {
					if ( !updated.containsKey( s ) || updated.get( s ) % 2 != 0 ) {
						ret = false;
						break;
					}
				}
			}
		} else {
			ret = false;
		}
		return ret;
	}
	
	private void sortAndAdd() {
		List<String> names = new ArrayList<>( io.keySet() );
		Collections.sort( names );
		names.forEach( n -> center.add( io.get( n ) ) );
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		io.values().forEach( l -> l.broadcastReceived( e ) );
	}
}