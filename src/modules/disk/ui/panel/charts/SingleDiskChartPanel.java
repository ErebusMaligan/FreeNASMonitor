package modules.disk.ui.panel.charts;

import java.awt.Dimension;
import java.util.Observable;

import data.DataPair;
import data.DataSet;
import data.TimeData;
import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.AbstractDynamicTimeSeriesChartPanel;
import fnmcore.util.ChartUtils;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import state.provider.ApplicationProvider;
import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:12:43 AM 
 */
public class SingleDiskChartPanel extends AbstractDynamicTimeSeriesChartPanel {

	private static final long serialVersionUID = 1L;

	private String device;
	
	private String dev;
	
	public SingleDiskChartPanel( ApplicationProvider state, String device ) {
		super( state, "[" + device.substring( device.lastIndexOf( "/" ) + 1 ) + "] Temperature", "Temperature (" + ApplicationConstants.DEGREE + "C)", state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ), ApplicationConstants.DISK_INTERVAL );
		GU.setSizes( this, new Dimension( 100, 350 ) );
		this.device = device;
		dev = device.substring( device.lastIndexOf( "/" ) + 1 );
		cd.addDataSet( new DataSet<TimeData, Number>( dev ) );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		TimeData d = new TimeData( System.currentTimeMillis() ); 
		if ( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( device ) != null ) {
			@SuppressWarnings("unchecked")
			DataSet<TimeData, Number> ds = (DataSet<TimeData, Number>)cd.getSetByName( dev );
			ds.add( new DataPair<TimeData, Integer>( d, Integer.parseInt( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( device ).temp ) ) );
		}
		ChartUtils.autoRange( cp );
		super.update( o, arg );
	}
}