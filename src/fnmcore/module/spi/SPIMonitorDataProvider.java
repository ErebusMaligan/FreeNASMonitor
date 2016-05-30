package fnmcore.module.spi;

import java.util.List;

import fnmcore.state.monitor.MonitorData;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Apr 25, 2016, 1:34:11 PM 
 */
public interface SPIMonitorDataProvider extends FNMModuleSPI {
	public List<MonitorData> getMonitorData();
}