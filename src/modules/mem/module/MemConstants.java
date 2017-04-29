package modules.mem.module;

public class MemConstants {

	//mem labels
	public static final String MEM_SIZE = "Size: ";
	
	public static final String MEM_TYPE = "Type: ";
	
	public static final String MEM_BANK = "Bank: ";
	
	public static final String MEM_SPEED = "Speed: ";

	//static mem stuff
	public static final String MEM_OVERALL_CMD = "sysctl hw | egrep 'hw.(user|real)'";  /*"grep -i memory /var/run/dmesg.boot";*/ //old version stopped working after latest freenas 9.3 update

	public static final String MEM_CURRENT_CMD = "top -b | grep Mem";

	public static final String MEM_PHYS_CMD = "dmidecode -t memory | grep -E 'Bank|Type:|Speed|Size' | grep -vE 'Error|Clock'";

	public static final String WD_MEM_METERS = "Memory Meters";
}