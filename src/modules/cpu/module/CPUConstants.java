package modules.cpu.module;

public class CPUConstants {

	public static final String FN_VER_CMD = "uname -a";
	
	//static cpu stuff
	public static final String CPU_INFO_CMD = "sysctl hw.model hw.ncpu"; //cpu type info
	
	public static final String CPU_SENSOR_INFO_CMD = "sysctl -a | grep -i 'temp.*desc.*cpu'"; //cpu thermal sensor info
	
	public static final String CPU_TEMP_CMD = "sysctl -a | grep 'cpu.*temp'"; //cpu temps + temp source
	
	public static final String CPU_USAGE_CMD = "top -P -d 2 -b | grep idle"; //cpu idle process

	public static final String CPU_FREQ_CMD = "sysctl dev.cpu | grep freq:"; //cpu idle process
	
	//static time stuff
	public static final String UPTIME_CMD = "uptime";

	public static final String SYSTIME_CMD = "date";

	//frame definitions
	public static final String WD_CPU_METERS = "CPU Meters";

	public static final String WD_CPU_IND_TEMP = "CPU Individual Temp.";

	public static final String WD_CPU_COMBINED_TEMP = "CPU Combined Temp.";

	public static final String WD_CPU_USAGE = "CPU Usage";

}
