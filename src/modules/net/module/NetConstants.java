package modules.net.module;

import fnmcore.constants.ApplicationConstants;

public class NetConstants {

	//static network monitor stuff
	public static final String NET_INFO_CMD = "netstat -b -i -n -I " + ApplicationConstants.NI_NAME + " | grep -v '<'";   //grab ipv4 info for interface listed
	
	public static final String NET_IN_PACKET = "In Packets";

	public static final String NET_OUT_PACKET = "Out Packets";

	public static final String NET_IN_BYTES = "In MBits";

	public static final String NET_OUT_BYTES = "Out MBits";

	
}
