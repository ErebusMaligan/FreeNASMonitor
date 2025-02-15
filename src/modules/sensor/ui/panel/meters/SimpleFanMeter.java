package modules.sensor.ui.panel.meters;

import java.awt.Dimension;

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.progress.EnhancedJProgressBar;
import listeners.BasicObservable;
import modules.cpu.module.CPUModule;
import modules.sensor.module.SensorModule;
import modules.sensor.state.data.SensorData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

public class SimpleFanMeter extends SimpleChart implements BroadcastListener {

	private static final long serialVersionUID = 1L;

	private EnhancedJProgressBar rpm;

	private String fanTitle;
	
	private boolean lightsOff = false;
	
	public SimpleFanMeter( String fanTitle, ApplicationProvider state ) {
		super( state, state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ) );
		dim = new Dimension( 75, dim.height );
		height = 12;
		this.fanTitle = fanTitle;
		rpm = addJProgressBar( 0, 2000, "RPM", "/2000" );
		setLabel( fanTitle );
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
		rpm.setValue( Double.valueOf( ( (SensorData)state.getMonitorManager().getDataByName( SensorModule.SENSOR_DATA ) ).getFanRPM( fanTitle ) ).intValue() );
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
			calculateSections( rpm );
		}
	}	
}