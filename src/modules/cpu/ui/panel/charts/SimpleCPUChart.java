package modules.cpu.ui.panel.charts;

import java.awt.Dimension;
import java.util.Observable;

import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.progress.EnhancedJProgressBar;

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
	
	public SimpleCPUChart( int core, ApplicationState state ) {
		super( state, state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ) );
		dim = new Dimension( 75, dim.height );
		height = 12;
		this.core = core;
		usage = addJProgressBar( 0, 100, "Usage", "%" );
		temp = addJProgressBar( 25, 80, "Temp", "(°C)" );
		setLabel( "Core " + core );
	}
	
	protected void calculateSections( EnhancedJProgressBar bar ) {
		try {
			bar.setSectionTwo( lightsOff ? UIUtils.lightsOff( AC.PROGRESS_BAR_MID, AC.LIGHTS_OFF ) : AC.PROGRESS_BAR_MID, (int)( bar.getMaximum() * .5 ) );
			bar.setSectionThree( lightsOff ? UIUtils.lightsOff( AC.PROGRESS_BAR_END, AC.LIGHTS_OFF ) : AC.PROGRESS_BAR_END, (int)( bar.getMaximum() * .75 ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		usage.setValue( new Float( ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getUsage( core ) ).intValue() );
		temp.setValue( new Float( ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getTemp( core ) ).intValue() ); 
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.EVENT_TYPE.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.EVENT_SETTING.ON ) {
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