package modules.log.module;

import modules.log.state.log.LogData;
import fnmcore.module.FNMModule;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 2, 2016, 1:48:17 PM 
 */
public class LogModule extends FNMModule {

	private LogData data;
	
	@Override
	public void init() {
		data = new LogData();
	}

	@Override
	public void shutdown() {}
	
	public LogData getLogData() {
		return data;
	}
}