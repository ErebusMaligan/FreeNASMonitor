package modules.disk.ui.panel.charts;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import statics.UIUtils;
import fnmcore.state.ApplicationState;
import fnmcore.util.ChartUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 6:09:38 PM 
 */
public class DiskChartPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs = new JTabbedPane();
	
	protected ApplicationState state;
	
	protected boolean init = false;
	
	public DiskChartPanel( ApplicationState state ) {
		this.state = state;
		UIUtils.setColors( this, tabs );
		UIUtils.setTabUI( tabs );
		ChartUtils.onceTimeFormat();
		this.setLayout( new BorderLayout() );
		tabs.add( "Combined Temp", new AllDiskTemperatureChartPanel( state ) );
//		tabs.add( "I/O", new DiskIOPanel( state ) );
		this.add( tabs, BorderLayout.CENTER );
		state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ).addObserver( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		if ( !init ) {
			JPanel p = new JPanel();
			p.setLayout( new GridLayout( 0, 2 ) );
			for ( String loc : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
				if ( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( loc ) != null ) {
					p.add( new SingleDiskChartPanel( state, loc ) );
				}
			}
			tabs.add( "Individual Temp", UIUtils.setJScrollPane( new JScrollPane( p ) ) );
			init = true;
		}
	}
}