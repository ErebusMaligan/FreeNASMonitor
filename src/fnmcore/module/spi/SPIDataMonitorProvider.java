package fnmcore.module.spi;

import java.util.List;

import fnmcore.state.ApplicationState;
import fnmcore.state.monitor.AbstractMonitor;
import fnmcore.state.ssh.SSHSession;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:27:57 PM 
 */
public interface SPIDataMonitorProvider extends FNMModuleSPI {
	
	public void initDataMonitors( ApplicationState state, SSHSession ssh );
	
	public List<AbstractMonitor> getDataMonitors();
}