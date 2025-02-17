package modules.net.ui.panel.net.charts;

import java.awt.BorderLayout;

import org.jfree.chart.ChartPanel;

import data.DataPair;
import data.DataSet;
import data.TimeData;
import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.AbstractDynamicTimeSeriesChartPanel;
import fnmcore.ui.panel.generic.raw.RawDataPanel;
import fnmcore.util.ChartUtils;
import listeners.BasicObservable;
import modules.net.module.NetModule;
import modules.net.state.net.data.NetworkData;
import state.provider.ApplicationProvider;
import visualization.chart.Chart;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:16:18 PM 
 */
public class NetDoubleChartPanel extends AbstractDynamicTimeSeriesChartPanel {

	private static final long serialVersionUID = 1L;

	private String[] values;
	
	public NetDoubleChartPanel( ApplicationProvider state, String title, String y, String[] values ) {
		super( state, title, y, state.getMonitorManager().getMonitorByName( NetModule.NET_MONITOR ), ApplicationConstants.NET_INTERVAL );
		this.values = values;
	}
	
	@Override
	protected void initChartPanel( long interval ) {
		cp = (ChartPanel)new Chart( cd, Chart.ChartType.TIME_SERIES_LINE_CHART ).getVisualization();
		ChartUtils.enhanceChart( cp );
		ChartUtils.setTimeSeriesDomainTickUnits( cp, interval );
		this.add( new RawDataPanel( cd ), BorderLayout.NORTH );
		this.add( cp, BorderLayout.CENTER );
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		TimeData d = new TimeData( System.currentTimeMillis() ); 
		for ( String c : values ) {
			if ( !init ) {
				cd.addDataSet( new DataSet<TimeData, Number>( c ) );
			}
			@SuppressWarnings("unchecked")
			DataSet<TimeData, Number> ds = (DataSet<TimeData, Number>)cd.getSetByName( c );
			ds.add( new DataPair<TimeData, Double>( d, ( (NetworkData)state.getMonitorManager().getDataByName( NetModule.NET_DATA ) ).getDoubleForKey( c ) ) );
		}
		ChartUtils.autoRange( cp );
		super.update( o, arg );
	}
}