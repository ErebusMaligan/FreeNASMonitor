package fnmcore.constants;

import java.awt.Color;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 10:15:34 PM 
 */
public class ApplicationConstants {
	
	//ssh stuff
	public static final String SSH_MASTER_PROCESS_NAME = "SSH";
	
	public static final String SSH_RT_PROCESS_NAME = "SSH_RT";
	
	//other
	public static final String DEGREE  = "\u00b0";
	
	//Intervals
	public static long REAL_TIME_DISK_INTERVAL = 1 * 1000;
	
	public static long DISK_INTERVAL = 20 * 1000;
	
	public static long CPU_INTERVAL = 2 * 1000;
	
	public static long NET_INTERVAL = 5 * 1000;
	
	//OTHER
	public static int CHART_VIEW_POINTS = 20;
	
	//NET
	public static String NI_NAME = "em0";
	
	//COLOR
	public static int LIGHTS_OFF = 4;
	
	public static Color READ_WARNING = Color.DARK_GRAY;
	
	public static Color PROGRESS_BAR_MID = Color.YELLOW;
	
	public static Color PROGRESS_BAR_END = Color.WHITE;
	

	//XML Tags

	
	public static final String XRW = "READWARNING";
	
	public static final String XPBM = "PROGRESSBARMID";
	
	public static final String XPBE = "PROGRESSBAREND";
	

	
	public static final String XINT = "INTERVALS";
	
	public static final String XDISK = "DISK";
	
	public static final String XCPU = "CPU";
	
	public static final String XNET = "NET";
	
	public static final String XRT = "REALTIME";
	
	public static final String XINTERFACE = "INTERFACE";
	
	public static final String XSETTINGS = "SETTINGS";
	
	
	
	
	public static final String SET_COLOR_RW = "Read Warnings: ";
	
	public static final String SET_COLOR_PBM = "Progress Bar Medium: ";
	
	public static final String SET_COLOR_PBE = "Progress Bar End: ";
	

	public static final String SET_INTERVAL_DIALOG = "Monitor Interval Settings";
	
	public static final String SET_DISK_INTERVAL = "SMART/Scrub Info: ";
	
	public static final String SET_CPU_INTERVAL = "CPU Info: ";
	
	public static final String SET_NET_INTERVAL = "Network Info: ";
	
	public static final String SET_RT_INTERVAL = "Disk I/O Info: ";
	
	
	public static final String SET_NET_INTERFACE_DIALOG = "Network Interface Settings";
	
	public static final String SET_NET_INTERFACE_NAME = "Remote Interface Name: ";

	
	//menubar
	public static final String MB_SSH = "SSH";
	
	public static final String MB_SSH_CONNECT = "Connect";
	
	public static final String MB_SSH_DISCONNECT = "Disconnect";
	
	public static final String MB_SYS_MONITOR = "System Monitor";
	
	public static final String MB_SYS_MONITOR_START = "Start Monitor";
	
	public static final String MB_SYS_MONITOR_STOP = "Stop Monitor";
	
	public static final String MB_LOGS = "Logs";
	
	public static final String MB_LOGS_WARNING = "View Warnings";
	
	public static final String MB_SETTINGS = "Settings";
	
	public static final String MB_COLOR = "Color";
	
	public static final String MB_FRAME = "Save Window Location";
	
	public static final String MB_MONITOR_INTERVALS = "Set Monitoring Intervals";
	
	public static final String MB_NETWORK_INTERFACE = "Network Interface";
	
	public static final String MB_DEBUG_MODE = "Debug Mode";
	
	public static final String MB_AUTO_START = "Auto Start";
	
	public static final String MB_WINDOW_MANAGEMENT = "Create Window";
	
	public static final String MB_TABS = "Tabs";
	
	public static final String MB_CREATE_TAB = "Create New Tab";
	
	public static final String MB_REMOVE_TAB = "Remove Current Tab";
	
	public static final String MB_WINDOWS = "Windows";
	
	public static final String MB_CPU = "CPU";
	
	public static final String MB_SYS = "System";
	
	public static final String MB_DISK = "Disk";
	
	public static final String MB_MEM = "Memory";
	
	public static final String MB_CHARTS = "Charts";
	
	public static final String MB_NET = "Network";
	
	
	//frame definitions
	public static final String WD_CONTROL_PANEL = "Control Panel";
	
	public static final String WD_MEM_METERS = "Memory Meters";
	
	public static final String WD_SCRUB_CONTROL = "Scrub Control";
	
	public static final String WD_SYS_INFO = "System Info";
	

	
	public static final String WD_CPU_USAGE = "CPU Usage";
	
	public static final String WD_CPU_COMBINED_TEMP = "CPU Combined Temp.";
	
	public static final String WD_CPU_IND_TEMP = "CPU Individual Temp.";
	
	public static final String WD_DISK_IND_TEMP = "Disk Individual Temp.";
	
	public static final String WD_DISK_COMBINED_TEMP = "Disk Combined Temp.";
	
	public static final String WD_NET_PACKETS = "Packets";
	
	public static final String WD_NET_BYTES = "MBits";
	

	public static final String B_DISK_SCRUB_START = "Start";
	
	public static final String B_DISK_SCRUB_STOP = "Stop";
	
	
	//control panel
	public static final String CON_LIGHTS = "LIGHTS";
	
	public static final String CON_DISK_MON = "DISK INFO";
	
	public static final String CON_RT_DISK_MON = "DISK I/O";
	
	public static final String CON_CPU_MON = "CPU";
	
	public static final String CON_MEM_MON = "MEMORY";
	
	public static final String CON_NET_MON = "NETWORK";
}