package modules.disk.module;

public class DiskConstants {

	//static disk stuff	
	public static final String DISK_LIST_CMD = "camcontrol devlist";

	public static final String DISK_SMART_INFO_CMD = "smartctl -a";
	
	public static final String DISK_SMART_INFO_CMD_POST = " | grep -E 'Model|Serial|health|Hours|Celsius|Capacity|INFORMATION|delay|usage\\ summary'";
	
	public static final String POOL_LIST_CMD = "zpool list";
	
	public static final String POOL_STATUS_CMD = "zpool status";
	
	public static final String MAP_GPTID_TO_DEVICE_CMD = "glabel status";
	
	public static final String DISK_IO_CMD = "gstat -b | grep -vE 'dT|L\\(q\\)|gptid|p2|p1|eli'";
	
	public static final String DISK_SCRUB_STATUS = "zpool status | grep -E 'pool|scan|done'";
	
	public static final String DISK_SCRUB_START = "zpool scrub ";
	
	public static final String DISK_SCRUB_STOP = "zpool scrub -s ";
	
	public static final String DISK_USAGE = "df | grep -Ev 'var|etc' | grep -iE '/mnt|/boot'";

	//frame definitions
	public static final String WD_DISK_INFO_TABLE = "Disk Info Table";

	public static final String WD_DISK_METERS = "Disk Meters";

	public static final String WD_DISK_SCRUB_TABLE = "Disk Scrub Table";

	public static final String B_DISK_SCRUB_START = "Start";

	public static final String B_DISK_SCRUB_STOP = "Stop";

	public static final String WD_DISK_COMBINED_TEMP = "Disk Combined Temp.";

	public static final String WD_DISK_IND_TEMP = "Disk Individual Temp.";

	public static final String WD_SCRUB_CONTROL = "Scrub Control";

}
