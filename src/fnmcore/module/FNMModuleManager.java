package fnmcore.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fnmcore.module.spi.FNMModuleSPI;
import modules.control.module.ControlModule;
import modules.cpu.module.CPUModule;
import modules.disk.module.DiskModule;
import modules.log.module.LogModule;
import modules.mem.module.MemModule;
import modules.net.module.NetModule;
import modules.system.module.SysModule;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 4:37:47 PM 
 */
public class FNMModuleManager {
	
	private Map<String, FNMModule> modules = new HashMap<>();
	
	{
		Arrays.asList( new FNMModule[] { new ControlModule(), new CPUModule(), new DiskModule(), new LogModule(), new MemModule(), new NetModule(), new SysModule() } ).forEach( m -> modules.put( m.getClass().getName(), m ) );
	}
	
	public List<FNMModule> getAllModules() {
		return Collections.unmodifiableList( new ArrayList<FNMModule>( modules.values() ) );
	}
	
	@SuppressWarnings("unchecked")
	public <T extends FNMModuleSPI> List<T> getModulesBySPIType( Class<T> spi ) {
		List<T> ret = new ArrayList<>();
		modules.values().forEach( m -> {
			if ( spi.isInstance( m ) ) {
				ret.add( (T)m );
			}
		} );
		return ret;//(List<T>)modules.values().stream().filter( m -> spi.isInstance( m ) ).collect( Collectors.toList() );
	}
	
	@SuppressWarnings("unchecked")
	public <T extends FNMModule> T getModuleByModuleClass( Class<T> module ) {
		return (T)modules.values().stream().filter( m -> module.isInstance( m ) ).findFirst().get();
	}
}