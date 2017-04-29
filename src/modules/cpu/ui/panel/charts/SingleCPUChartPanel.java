package modules.cpu.ui.panel.charts;

import java.awt.Dimension;
import java.util.Observable;

import data.DataPair;
import data.DataSet;
import data.TimeData;
import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.AbstractDynamicTimeSeriesChartPanel;
import fnmcore.util.ChartUtils;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import state.provider.ApplicationProvider;
import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:12:43 AM 
 */
public class SingleCPUChartPanel extends AbstractDynamicTimeSeriesChartPanel {

	private static final long serialVersionUID = 1L;

	private int cpu;
	
	private String c;
	
	private static final String CPU = "CPU ";
	
	public SingleCPUChartPanel( ApplicationProvider state, int cpu ) {
		super( state, "[" + CPU + cpu + "] Temperature", "Temperature (" + ApplicationConstants.DEGREE + "C)", state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ), ApplicationConstants.CPU_INTERVAL );
		GU.setSizes( this, new Dimension( 100, 350 ) );
		this.cpu = cpu;
		c = CPU + cpu;
		cd.addDataSet( new DataSet<TimeData, Number>( c ) );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		TimeData d = new TimeData( System.currentTimeMillis() );
		@SuppressWarnings("unchecked")
		DataSet<TimeData, Number> ds = (DataSet<TimeData, Number>)cd.getSetByName( c );
		ds.add( new DataPair<TimeData, Float>( d, ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getTemp( cpu ) ) );
		ChartUtils.autoRange( cp );
		super.update( o, arg );
	}
}