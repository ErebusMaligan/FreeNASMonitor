package modules.cpu.ui.panel.charts;

import java.awt.Dimension;

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.progress.EnhancedJProgressBar;
import listeners.BasicObservable;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 9, 2015, 9:27:38 PM 
 */
public class SimpleCPUChart extends SimpleChart implements BroadcastListener {

	private static final long serialVersionUID = 1L;

	private EnhancedJProgressBar temp, usage;

	private int core;
	
	private boolean lightsOff = false;
	
	public SimpleCPUChart( int core, ApplicationProvider state ) {
		super( state, state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ) );
		dim = new Dimension( 75, dim.height );
		height = 12;
		this.core = core;
		usage = addJProgressBar( 0, 100, "Usage", "%" );
		temp = addJProgressBar( 25, 80, "Temp", "(" + ApplicationConstants.DEGREE + "C)" );
		setLabel( "Core " + core );
	}
	
	protected void calculateSections( EnhancedJProgressBar bar ) {
		try {
			bar.setSectionTwo( lightsOff ? UIUtils.lightsOff( ApplicationConstants.PROGRESS_BAR_MID, ApplicationConstants.LIGHTS_OFF ) : ApplicationConstants.PROGRESS_BAR_MID, (int)( bar.getMaximum() * .5 ) );
			bar.setSectionThree( lightsOff ? UIUtils.lightsOff( ApplicationConstants.PROGRESS_BAR_END, ApplicationConstants.LIGHTS_OFF ) : ApplicationConstants.PROGRESS_BAR_END, (int)( bar.getMaximum() * .75 ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		usage.setValue( Float.valueOf( ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getUsage( core ) ).intValue() );
		temp.setValue( Float.valueOf( ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getTemp( core ) ).intValue() ); 
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				UIUtils.setColorsRecursive( this );
				lightsOff = false;
			} else {
				lightsOff = true;
				UIUtils.setColorsRecursiveOff( this );
			}
			calculateSections( temp );
			calculateSections( usage );
		}
	}	
}