package modules.log.state.log;

import java.util.Observable;
import java.util.Observer;

import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import modules.log.module.LogModule;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 3, 2015, 7:23:22 AM 
 */
public class WarningMonitor implements Observer {
	
	private ApplicationState state;
	
	public WarningMonitor( ApplicationState state ) {
		this.state = state;
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		if ( o.equals( state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ) ) ) {
			for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
				float t = ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getTemp( i );
				if ( t > AC.CPU_TEMP_WARN_T ) {
					( state.getModuleManager().getModuleByModuleClass( LogModule.class ) ).getLogData().add( LogEntry.createWarning( AC.CPU_TEMP_WARN + i, "CPU Temperature Warning: core[" + i + "] - " + t + " degrees Celsius" ) );
				}
			}
		}
	}
}