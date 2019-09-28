package modules.cpu.state.data;

import fnmcore.constants.ApplicationConstants;
import modules.cpu.module.CPUConstants;
import process.ProcessManager;
import process.io.ProcessStreamSiphon;
import state.monitor.MonitorData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 27, 2015, 4:39:36 AM 
 */
public class CPUData extends MonitorData implements ProcessStreamSiphon {
	
	private int cpuCount = 0;
	
	private String sysTime;
	
	private String uptime;

	private String model;
	
	private String source;
	
	private float[] temps;
	
	private float[] usages;
	
	private String version;
	
	public CPUData() {
		skimmers.put( CPUConstants.CPU_INFO_CMD, line -> {
			if ( line.startsWith( "hw.model" ) ) {
				model = sysctl( line );
			} else if ( line.startsWith( "hw.ncpu" ) ) {
				cpuCount = Integer.parseInt( sysctl( line ) );
				temps = new float[ cpuCount ];
				usages = new float[ cpuCount ];
			}
		} );
		
		skimmers.put( CPUConstants.CPU_SENSOR_INFO_CMD, line -> {
			if ( line.contains( "dev" ) ) {
				source = sysctl( line );
			}
		} );
		
		skimmers.put( CPUConstants.UPTIME_CMD, line -> {
			if ( line.contains( "load averages" ) ) {
				uptime = line.substring( 0, line.indexOf( "load" ) -2 );
			}
		} );
		
		skimmers.put( CPUConstants.SYSTIME_CMD, line -> {
			if ( line.matches( ".*?\\d\\d\\p{Punct}\\d\\d\\p{Punct}\\d\\d.*?" ) ) {
				sysTime = line;
			}
		} );
		
		skimmers.put( CPUConstants.CPU_TEMP_CMD, line -> {
			if ( line.startsWith( "dev.cpu" ) ) {
				int num = cpuNum( line );
				temps[ num ] = temp( line );
			}
		} );
		
		skimmers.put( CPUConstants.CPU_USAGE_CMD, line -> {
			if ( line.startsWith( "CPU" ) ) {
				int num = cpuUsageNum( line );
				usages[ num ] = 100.0f - Float.parseFloat( line.trim().substring( line.lastIndexOf( "," ) + 1, line.lastIndexOf( "%" ) ).trim() );
			}
		} );
		
		skimmers.put( CPUConstants.FN_VER_CMD, line -> {
			if ( line.startsWith( "FreeBSD" ) ) {
				version = line.split( " " )[ 2 ];
			}
		} );
		
		ProcessManager.getInstance().registerSiphon( ApplicationConstants.SSH_MASTER_PROCESS_NAME, this );
	}
	
	private String sysctl( String line ) {
		return line.substring( line.indexOf( ":" ) + 1 ).trim();
	}
	
	private float temp( String line ) {
		String sub = sysctl( line );
		return Float.parseFloat( sub.substring( 0, sub.indexOf( "C" ) ) );
	}
	
	private int cpuNum( String line ) {
		int i = line.indexOf( "cpu." );
		return Integer.parseInt( line.substring( i + 4, i + 5 ) ); 
	}
	
	private int cpuUsageNum( String line ) {
		int i = line.indexOf( ":" );
		return Integer.parseInt( line.substring( i - 1, i ) );
	}
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * @return the cpuCount
	 */
	public int getCpuCount() {
		return cpuCount;
	}
	
	public float getTemp( int cpu ) {
		return temps[ cpu ];
	}
	
	public float getUsage( int cpu ) {
		return usages[ cpu ];
	}
	
	public String getSysTime() {
		return sysTime;
	}
	
	public String getUpTime() {
		return uptime;
	}
	
	public String getVersion() {
		return version;
	}
}