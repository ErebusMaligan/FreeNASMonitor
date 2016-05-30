package modules.disk.ui.panel.charts;

import java.util.Observable;

import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import data.DataPair;
import data.DataSet;
import data.TimeData;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.ui.panel.generic.charts.AbstractDynamicTimeSeriesChartPanel;
import fnmcore.util.ChartUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:16:18 PM 
 */
public class AllDiskTemperatureChartPanel extends AbstractDynamicTimeSeriesChartPanel {

	private static final long serialVersionUID = 1L;

	public AllDiskTemperatureChartPanel( ApplicationState state ) {
		super( state, "Disk Temperature", "Temperature (�C)", state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ), AC.DISK_INTERVAL );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		TimeData d = new TimeData( System.currentTimeMillis() ); 
		for ( String loc : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
			String dev = loc.substring( loc.lastIndexOf( "/" ) + 1 );
			if ( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( loc ) != null ) {
				if ( !init ) {
					cd.addDataSet( new DataSet<TimeData, Number>( dev ) );
				}
				@SuppressWarnings("unchecked")
				DataSet<TimeData, Number> ds = (DataSet<TimeData, Number>)cd.getSetByName( dev );
				if ( ds != null ) {  //TODO: fix this issue... it's null if the process got partially completed when the ssh session got shut down, but then got restarted
									//basically, the whole init loop has to be fixed to restart for this panel if the ssh session restarts
					ds.add( new DataPair<TimeData, Integer>( d, Integer.parseInt( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( loc ).temp ) ) );
				}
			}
		}
		ChartUtils.autoRange( cp );
		super.update( o, arg );
	}
}