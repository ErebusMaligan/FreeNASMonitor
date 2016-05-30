package fnmcore.module.spi;

import java.util.List;

import fnmcore.state.ApplicationState;
import fnmcore.state.monitor.AbstractMonitor;
import fnmcore.state.ssh.SSHSession;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:41:08 PM 
 */
public interface SPIRealTimeMonitorProvider extends FNMModuleSPI {
	public void initRTDataMonitors( ApplicationState state, SSHSession ssh );
	
	public List<AbstractMonitor> getRTDataMonitors();
}