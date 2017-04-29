package modules.net.state.net.data;

import fnmcore.constants.ApplicationConstants;
import modules.net.module.NetConstants;
import process.ProcessManager;
import process.io.ProcessStreamSiphon;
import state.monitor.MonitorData;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 29, 2015, 9:00:04 PM 
 */
public class NetworkData extends MonitorData implements ProcessStreamSiphon {

	private String name;
	
	private String mtu;
	
	private String ip;
	
	private long iPS = -1l;
	
	private long iBS = -1l;
	
	private long oPS = -1l;
	
	private long oBS = -1l;
	
	private long iP = -1l;
	
	private long iB = -1l;
	
	private long oP = -1l;
	
	private long oB = -1l;
	
	private long prevTime = -1l;
	
	private long curTime= -1l;
	
	private double factor = 125000;
	
	public NetworkData() {
		skimmers.put( NetConstants.NET_INFO_CMD, line -> {
			if ( line.contains( ApplicationConstants.NI_NAME ) && !line.contains( "grep" ) ) {
				prevTime = curTime;
				curTime = System.currentTimeMillis();
				String[] param = line.trim().replaceAll( " +", " " ).split( " " );
				name = param[ 0 ];
				mtu = param[ 1 ];
				ip = param[ 3 ];
				iPS = iP;
				iP = Long.parseLong( param[ 4 ] );
				if ( iPS == -1l ) {
					iPS = iP;
				}
				iBS = iB;
				iB = Long.parseLong( param[ 7 ] );
				if ( iBS == -1l ) {
					iBS = iB;
				}
				oPS = oP;
				oP = Long.parseLong( param[ 8 ] );
				if ( oPS == -1l ) {
					oPS = oP;
				}
				oBS = oB;
				oB = Long.parseLong( param[ 10 ] );
				if ( oBS == -1l ) {
					oBS = oB;
				}
			}
		} );
		
		ProcessManager.getInstance().registerSiphon( ApplicationConstants.SSH_MASTER_PROCESS_NAME, this );
	}
	
	public double getDoubleForKey( String key ) {
		double ret = -1;
		switch ( key ) {
			case NetConstants.NET_IN_PACKET: ret = getIPPS(); break;
			case NetConstants.NET_OUT_PACKET: ret = getOPPS(); break;
			case NetConstants.NET_IN_BYTES: ret = getIBPS(); break;
			case NetConstants.NET_OUT_BYTES: ret = getOBPS(); break;
		}
		return ret;
	}
	
	public double getIPPS() {
		return ( iP - iPS ) / getTimeDiff();
	}
	
	public double getOPPS() {
		return ( oP - oPS ) / getTimeDiff();
	}
	
	public double getIBPS() {
		return ( iB - iBS ) / getTimeDiff() / factor;
	}
	
	public double getOBPS() {
		return ( oB - oBS ) / getTimeDiff() / factor;
	}
	
	public double getTimeDiff() {
		return ( curTime - prevTime ) / 1000d;
	}
	
	public String getMTU() {
		return mtu;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIP() {
		return ip;
	}
	
	@Override
	public void notifyProcessEnded( String arg0 ) {}

	@Override
	public void notifyProcessStarted( String arg0 ) {}
}