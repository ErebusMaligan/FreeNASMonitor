package modules.sensor.state.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fnmcore.constants.ApplicationConstants;
import modules.sensor.module.SensorConstants;
import process.ProcessManager;
import state.monitor.MonitorData;

public class SensorData extends MonitorData {

	private Map<String, Double> fanData = new HashMap<>();
	
	public SensorData() {
		skimmers.put( SensorConstants.SENSOR_LIST_CMD, line -> {
			if ( line.startsWith( "FAN" ) && !line.contains( "na" ) ) {
				String[] sensorData = line.split( "\\|" );
				fanData.put( sensorData[ 0 ].trim(), Double.parseDouble( sensorData[ 1 ].trim() ) );
			}
		} );
		ProcessManager.getInstance().registerSiphon( ApplicationConstants.SSH_MASTER_PROCESS_NAME, this );
	}
	
	public List<String> getFanNames() {
		List<String> ret = new ArrayList<>( fanData.keySet() );
		Collections.sort( ret );
		return ret;
	}
	
	public Double getFanRPM( String name ) {
		return fanData.get( name );
	}
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}
}