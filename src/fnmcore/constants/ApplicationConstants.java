package fnmcore.constants;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;
import xml.XMLExpansion;
import xml.XMLValues;
import fnmcore.state.ApplicationState;
import gui.windowmanager.WindowManagerConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 10:15:34 PM 
 */
public class ApplicationConstants implements XMLValues {
	
	private ApplicationState state;
	
	//THIS IS ONLY FOR THE SUBCLASS AC TO USE FOR ACCESS TO STATIC ELEMENTS
	public ApplicationConstants() {
		
	}
	
	public ApplicationConstants( ApplicationState state ) {
		this.state = state;
	}
	
	//dynamic properties
	public static boolean DEBUG = false;
	
	public static boolean AUTO = false;
	
	
	//SSH
	public static String SSH_USER = "root";

	public static String SSH_IP = "192.168.10.199";

	public static String SSH_PORT = "22";
	
	public static String SSH_PW = "";
	
	public static String SSH_APP_PATH = "C:\\Program Files (x86)\\PuTTY";
	
	public static boolean SSH_USE_KEY = false;
	
	public static String SSH_KEY_PATH = "";
	
	
	
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
	
	public static Color BACKGROUND = Color.BLACK;
	
	public static Color FOREGROUND = Color.RED;
	
	public static Color FOREGROUND_DARKER = FOREGROUND.darker().darker().darker().darker();
	
	public static Color TRANSPARENT_BG = new Color( BACKGROUND.getRed(), BACKGROUND.getGreen(), BACKGROUND.getBlue(), 96 );
	
	public static Color READ_WARNING = Color.DARK_GRAY;
	
	public static Color PROGRESS_BAR_MID = Color.YELLOW;
	
	public static Color PROGRESS_BAR_END = Color.WHITE;
	
	
	//WARNING Thresholds
	public static float CPU_TEMP_WARN_T = 35.0f;
	
	
	//WARNING Types
	public static String CPU_TEMP_WARN = "CPU TEMP WARNING ";
	
	
	//frame coords
	public static int FRAME_MAX_STATE = JFrame.NORMAL; 
	
	public static int FRAME_W = 800;
	
	public static int FRAME_H = 600;
	
	public static int FRAME_X = 0;
	
	public static int FRAME_Y = 0;
	
	public static String FRAME_SCREEN = null;
	
	
	
	
	//XML Tags
	public static final String XFG = "FOREGROUND";
	
	public static final String XBG = "BACKGROUND";
	
	public static final String XRW = "READWARNING";
	
	public static final String XPBM = "PROGRESSBARMID";
	
	public static final String XPBE = "PROGRESSBAREND";
	
	public static final String XCOLORS = "COLORS";
	
	public static final String XSSH = "SSH";
	
	public static final String XSSHU = "USER";
	
	public static final String XSSHPW = "PASS";
	
	public static final String XSSHIP = "IP";
	
	public static final String XSSHPORT = "PORT";
	
	public static final String XSSHUSEKEY = "USE_KEY";
	
	public static final String XSSHKEYPATH = "KEY_PATH";
	
	public static final String XPLINK = "PLINK_PATH";
	
	public static final String XWINDOW = "WINDOW";
	
	public static final String XWH = "HEIGHT";
	
	public static final String XWW = "WIDTH";
	
	public static final String XWX = "X";
	
	public static final String XWY = "Y";
	
	public static final String XWM = "MAX";
	
	public static final String XWS = "SCREEN";
	
	public static final String XINT = "INTERVALS";
	
	public static final String XDISK = "DISK";
	
	public static final String XCPU = "CPU";
	
	public static final String XNET = "NET";
	
	public static final String XRT = "REALTIME";
	
	public static final String XDEBUG = "DEBUG";
	
	public static final String XINTERFACE = "INTERFACE";
	
	public static final String XAUTO = "AUTO";
	
	public static final String XSETTINGS = "SETTINGS";

	public static ImageIcon APP_ICON = new ImageIcon( ApplicationConstants.class.getResource( "freenas.png" ) );
	
	private AutoSettings autoS = new AutoSettings();

	private NetSettings netS = new NetSettings();
	
	private DebugSettings debugS = new DebugSettings();
	
	private IntervalSettings intS = new IntervalSettings();
	
	private WindowSettings winS = new WindowSettings();
	
	private SSHSettings sshS = new SSHSettings();
	
	private ColorSettings colorS = new ColorSettings();
	
	@Override
	public List<XMLValues> getChildNodes() { 
		return Arrays.asList( new XMLValues[] { debugS, autoS, netS, intS, winS, sshS, colorS, state.getUIManager().getTabManager() } ); 
	}
	
	@Override
	public void loadParamsFromXMLValues( XMLExpansion root ) {
		XMLExpansion e = root;
		if ( root.getChild( XSETTINGS ) != null ) {
			e = root.getChild( XSETTINGS );
		}
		if ( e.getChild( XNET ) != null ) {
			netS.loadParamsFromXMLValues( e.getChild( XNET ) );
		}
		if ( e.getChild( XAUTO ) != null ) {
			autoS.loadParamsFromXMLValues( e.getChild( XAUTO ) );
		}
		if ( e.getChild( XDEBUG ) != null ) {
			debugS.loadParamsFromXMLValues( e.getChild( XDEBUG ) );
		}
		if ( e.getChild( XINT ) != null ) {
			intS.loadParamsFromXMLValues( e.getChild( XINT ) );
		}
		if ( e.getChild( XWINDOW ) != null ) {
			winS.loadParamsFromXMLValues( e.getChild( XWINDOW ) );
		}
		if ( e.getChild( XSSH ) != null ) {
			sshS.loadParamsFromXMLValues( e.getChild( XSSH ) );
		}
		if ( e.getChild( XCOLORS ) != null ) {
			colorS.loadParamsFromXMLValues( e.getChild( XCOLORS ) );
		}
		if ( e.getChild( WindowManagerConstants.XTABS ) != null ) {
			state.getUIManager().getTabManager().loadParamsFromXMLValues( e.getChild( WindowManagerConstants.XTABS ) );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<>();
		ret.put( XSETTINGS, values );
		return ret;
	}
	
	public static final String SET_COLOR_DIALOG = "Application Color Settings";
	
	public static final String SET_COLOR_FG = "Foreground: ";
	
	public static final String SET_COLOR_BG = "Background: ";
	
	public static final String SET_COLOR_RW = "Read Warnings: ";
	
	public static final String SET_COLOR_PBM = "Progress Bar Medium: ";
	
	public static final String SET_COLOR_PBE = "Progress Bar End: ";
	
	
	public static final String SET_SSH_DIALOG = "SSH Settings";
	
	public static final String SET_SSH_IP = "IP: ";
	
	public static final String SET_SSH_USER = "User: ";
	
	public static final String SET_SSH_PASS = "Password: ";
	
	public static final String SET_SSH_PATH = "SSH Path (can be empty): ";
	
	public static final String SET_USE_SSH_KEY = "Use RSA-2 Key";
	
	public static final String SET_SSH_KEY_PATH = "Private Key Path: ";
	
	public static final String SET_SSH_PORT = "SSH Port: ";
	
	
	public static final String SET_INTERVAL_DIALOG = "Monitor Interval Settings";
	
	public static final String SET_DISK_INTERVAL = "SMART/Scrub Info: ";
	
	public static final String SET_CPU_INTERVAL = "CPU Info: ";
	
	public static final String SET_NET_INTERVAL = "Network Info: ";
	
	public static final String SET_RT_INTERVAL = "Disk I/O Info: ";
	
	
	public static final String SET_NET_INTERFACE_DIALOG = "Network Interface Settings";
	
	public static final String SET_NET_INTERFACE_NAME = "Remote Interface Name: ";
	
	public static final String SET_TAB_NAME_DIALOG = "Name Tab";
	
	public static final String SET_TAB_NAME = "New Tab Name";

	
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
	
	public static final String WD_CPU_METERS = "CPU Meters";
	
	public static final String WD_DISK_INFO_TABLE = "Disk Info Table";
	
	public static final String WD_DISK_METERS = "Disk Meters";
	
	public static final String WD_DISK_SCRUB_TABLE = "Disk Scrub Table";
	
	public static final String WD_MEM_METERS = "Memory Meters";
	
	public static final String WD_SCRUB_CONTROL = "Scrub Control";
	
	public static final String WD_SYS_INFO = "System Info";
	
	public static final String WD_SSH = "SSH Session Logs";
	
	public static final String WD_CPU_USAGE = "CPU Usage";
	
	public static final String WD_CPU_COMBINED_TEMP = "CPU Combined Temp.";
	
	public static final String WD_CPU_IND_TEMP = "CPU Individual Temp.";
	
	public static final String WD_DISK_IND_TEMP = "Disk Individual Temp.";
	
	public static final String WD_DISK_COMBINED_TEMP = "Disk Combined Temp.";
	
	public static final String WD_NET_PACKETS = "Packets";
	
	public static final String WD_NET_BYTES = "MBits";
	
	public static final String WD_OUTPUT_LOG = "Output Log";
	
	
	//Title borders on system panel
	public static final String TB_SYS = "System Info";
	
	public static final String TB_CPU = "CPU Info";
	
	public static final String TB_DISK = "Disk Info";
	
	public static final String TB_SCRUB = "Scrub Control";
	
	public static final String TB_MEM = "Memory Info";
	
	
	
	public static final String B_DISK_SCRUB_START = "Start";
	
	public static final String B_DISK_SCRUB_STOP = "Stop";
	
	//other ui
	public static final String ACK = "Acknowledge";
	
	public static final String DEL = "Delete";
	
	
	//control panel
	public static final String CON_LIGHTS = "LIGHTS";
	
	public static final String CON_DISK_MON = "DISK INFO";
	
	public static final String CON_RT_DISK_MON = "DISK I/O";
	
	public static final String CON_CPU_MON = "CPU";
	
	public static final String CON_MEM_MON = "MEMORY";
	
	public static final String CON_NET_MON = "NETWORK";
	
	
	//static time stuff
	public static final String UPTIME_CMD = "uptime";
	
	public static final String SYSTIME_CMD = "date";
	
	//static cpu stuff
	public static final String CPU_INFO_CMD = "sysctl hw.model hw.ncpu"; //cpu type info
	
	public static final String CPU_SENSOR_INFO_CMD = "sysctl -a | grep -i 'temp.*desc.*cpu'"; //cpu thermal sensor info
	
	public static final String CPU_TEMP_CMD = "sysctl -a | grep 'cpu.*temp'"; //cpu temps + temp source
	
	public static final String CPU_USAGE_CMD = "top -P -d 2 -b | grep idle"; //cpu idle process
	
	//static mem stuff
	public static final String MEM_OVERALL_CMD = "sysctl hw | egrep 'hw.(user|real)'";  /*"grep -i memory /var/run/dmesg.boot";*/ //old version stopped working after latest freenas 9.3 update
	
	public static final String MEM_CURRENT_CMD = "top -b | grep Mem";
	
	public static final String MEM_PHYS_CMD = "dmidecode -t memory | grep -E 'Bank|Type:|Speed|Size' | grep -vE 'Error|Clock'";
	
	
	//static network monitor stuff
	public static final String NET_INFO_CMD = "netstat -b -i -n -I " + NI_NAME + " | grep -v '<'";   //grab ipv4 info for interface listed
	
	public static final String NET_IN_PACKET = "In Packets";
	
	public static final String NET_OUT_PACKET = "Out Packets";
	
	public static final String NET_IN_BYTES = "In MBits";
	
	public static final String NET_OUT_BYTES = "Out MBits";
	
	
	//static system panel labels
	public static final String SIP_MODEL = "CPU Model: ";
	
	public static final String SIP_UPTIME = "Uptime: ";
	
	public static final String SIP_SYSTIME = "Current Time: ";
	
	public static final String SIP_SOURCE = "CPU Temperature Sensors: ";
	
	public static final String SIP_CPU_COUNT = "CPU Cores: ";
	
	//mem labels
	public static final String MEM_SIZE = "Size: ";
	
	public static final String MEM_TYPE = "Type: ";
	
	public static final String MEM_BANK = "Bank: ";
	
	public static final String MEM_SPEED = "Speed: ";
	
	
	
	//ssh stuff
	public static final String SSH_MASTER_PROCESS_NAME = "SSH";
	
	public static final String SSH_RT_PROCESS_NAME = "SSH_RT";

	public static final String ECHO_CMD = "echo";
	
	public static final String COMPLETE = "complete";
	
	
	//static disk stuff	
	public static final String SSH_APPLICATION = TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ? "plink" : "ssh";
	
	public static final String SSH_HOST_COMMAND = "-ssh";

	public static final String SSH_TTY_FIX = "-tt";
	
	public static final String SSH_PORT_COMMAND = TerminalWindowManager.getInstance().OS == OSTerminalSettings.WINDOWS ? "-P" : "-p";
	
	public static final String SSH_PW_COMMAND = "-pw";
	
	public static final String SSH_KEY_COMMAND = "-i";
	
	public static final String SSH_AT = "@";
	
	public static final String SSH_EXIT = "logout";

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
}