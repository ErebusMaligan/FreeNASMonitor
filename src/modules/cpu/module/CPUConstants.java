package modules.cpu.module;

public class CPUConstants {

	//static cpu stuff
	public static final String CPU_INFO_CMD = "sysctl hw.model hw.ncpu"; //cpu type info
	
	public static final String CPU_SENSOR_INFO_CMD = "sysctl -a | grep -i 'temp.*desc.*cpu'"; //cpu thermal sensor info
	
	public static final String CPU_TEMP_CMD = "sysctl -a | grep 'cpu.*temp'"; //cpu temps + temp source
	
	public static final String CPU_USAGE_CMD = "top -P -d 2 -b | grep idle"; //cpu idle process

	//static time stuff
	public static final String UPTIME_CMD = "uptime";

	public static final String SYSTIME_CMD = "date";

	//frame definitions
	public static final String WD_CPU_METERS = "CPU Meters";

}
