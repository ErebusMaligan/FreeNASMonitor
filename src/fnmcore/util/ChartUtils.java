package fnmcore.util;

import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartPanel;

import visualization.module.jfreechart.JFreeChartEnhancer;
import data.ChartData;
import data.TimeData;
import fnmcore.constants.AC;
import fnmcore.constants.ApplicationConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 26, 2015, 4:25:44 AM 
 */
public class ChartUtils {
	
	public static void onceTimeFormat() {
		TimeData.formatter = new SimpleDateFormat( "HH:mm:ss" );  //this is program-wide
	}
	
	public static void enhanceChart( ChartPanel cp ) {
		JFreeChartEnhancer.setStandardChartColors( cp, ApplicationConstants.FOREGROUND, ApplicationConstants.BACKGROUND, ApplicationConstants.BACKGROUND, Color.WHITE );
		JFreeChartEnhancer.setPlotAxisColor( cp, ApplicationConstants.FOREGROUND );
		JFreeChartEnhancer.setPlotShowShapes( cp, JFreeChartEnhancer.getStandardShape() );
	}
	
	public static void setTimeSeriesDomainTickUnits( ChartPanel cp, long interval ) {
		JFreeChartEnhancer.setTimeSeriesDomainTickUnits( cp, (SimpleDateFormat)TimeData.formatter, ChartData.TimeGranularity.SECOND, calculateTickInterval( interval ) ); 
	}
	
	public static void setTimeSeriesRangeUnits( ChartPanel cp ) {
		JFreeChartEnhancer.setNumberRangeAxisTickUnits( cp, 1 );
	}
	
	public static void enhanceTimeSeriesChart( ChartPanel cp, long interval ) {
		setTimeSeriesDomainTickUnits( cp, interval );
		setTimeSeriesRangeUnits( cp );
	}
	
	/**
	 * this is my own calculation to come up with no more than 8 labels to avoid crowding
	 */
	private static int calculateTickInterval( long interval ) {
			//seconds between refresh * 1.5 (amount times the interval it actually takes for a refresh cycle to fully complete ) * points that will be viewed on a chart / 8
		return (int)( ( interval / 1000 ) * 1.5 * ( AC.CHART_VIEW_POINTS  / 8 ) );
	}
	
	public static void autoRange( ChartPanel cp ) {
		cp.restoreAutoBounds();
	}
}