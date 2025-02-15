package fnmcore.ui.panel.generic.charts;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import data.ChartData;
import data.DataSet;
import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.raw.RawDataPanel;
import fnmcore.util.ChartUtils;
import listeners.BasicObservable;
import listeners.BasicObserver;
import state.provider.ApplicationProvider;
import visualization.chart.Chart;
import visualization.module.jfreechart.JFreeChartEnhancer;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 3:26:55 PM 
 */
public abstract class AbstractDynamicTimeSeriesChartPanel extends JPanel implements BasicObserver {

	private static final long serialVersionUID = 1L;

	protected ApplicationProvider state;
	
	protected ChartData cd;
	
	protected ChartPanel cp;
	
	protected boolean init = false;
	
	public AbstractDynamicTimeSeriesChartPanel( ApplicationProvider state, String title, String yAxis, BasicObservable observable, long interval ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
//		this.setBorder( BorderFactory.createLineBorder( Color.DARK_GRAY ) );
		cd = new ChartData( new ArrayList<DataSet<?, ?>>(), title, "Time", yAxis );
		cd.setDynamic( true );
		cd.setViewableRange( ApplicationConstants.CHART_VIEW_POINTS );
		cd.setTimeGranularity( ChartData.TimeGranularity.SECOND );
		initChartPanel( interval );
		observable.addObserver( this );
	}
	
	protected void initChartPanel( long interval ) {
		cp = (ChartPanel)new Chart( cd, Chart.ChartType.TIME_SERIES_LINE_CHART ).getVisualization();
		ChartUtils.enhanceChart( cp );
		ChartUtils.enhanceTimeSeriesChart( cp, interval );
		this.add( new RawDataPanel( cd ), BorderLayout.NORTH );
		this.add( cp, BorderLayout.CENTER );
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( !init ) {
			JFreeChartEnhancer.setPlotShowShapes( cp, JFreeChartEnhancer.getStandardShape() ); //this part has to be run again because new series were added
			init = true;
		}
	}
}