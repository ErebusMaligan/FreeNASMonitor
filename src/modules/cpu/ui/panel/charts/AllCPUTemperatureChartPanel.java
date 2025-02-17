package modules.cpu.ui.panel.charts;

import data.DataPair;
import data.DataSet;
import data.TimeData;
import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.AbstractDynamicTimeSeriesChartPanel;
import fnmcore.util.ChartUtils;
import listeners.BasicObservable;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import state.provider.ApplicationProvider;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:16:18 PM 
 */
public class AllCPUTemperatureChartPanel extends AbstractDynamicTimeSeriesChartPanel {

	private static final long serialVersionUID = 1L;

	public AllCPUTemperatureChartPanel( ApplicationProvider state ) {
		super( state, "CPU Temperature", "Temperature (" + ApplicationConstants.DEGREE + "C)", state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ), ApplicationConstants.CPU_INTERVAL );
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		TimeData d = new TimeData( System.currentTimeMillis() ); 
		for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
			int cpu = i;
			String c = "CPU " + i;
			if ( !init ) {
				cd.addDataSet( new DataSet<TimeData, Number>( c ) );
			}
			@SuppressWarnings("unchecked")
			DataSet<TimeData, Number> ds = (DataSet<TimeData, Number>)cd.getSetByName( c );
			ds.add( new DataPair<TimeData, Float>( d, ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getTemp( cpu ) ) );
		}
		ChartUtils.autoRange( cp );
		super.update( o, arg );
	}
}