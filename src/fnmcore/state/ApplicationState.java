package fnmcore.state;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import modules.log.state.log.WarningMonitor;
import statics.LAFUtils;
import fnmcore.constants.AC;
import fnmcore.constants.ApplicationConstants;
import fnmcore.constants.ApplicationConstantsDocumentHandler;
import fnmcore.module.FNMModuleManager;
import fnmcore.module.spi.SPIDataMonitorProvider;
import fnmcore.module.spi.SPIMonitorDataProvider;
import fnmcore.module.spi.SPIRealTimeMonitorProvider;
import fnmcore.module.spi.SPIWindowDefinitionProvider;
import fnmcore.state.monitor.MonitorManager;
import fnmcore.state.ssh.SSHManager;
import fnmcore.ui.UIManager;
import fnmcore.ui.window.definition.OutputLogDefinition;
import fnmcore.ui.window.definition.SSHSessionDefinition;
import fnmcore.util.ChartUtils;
import gui.windowmanager.WindowManager;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 10:19:39 PM 
 */
public class ApplicationState {
	
	private FNMModuleManager manager = new FNMModuleManager();
	
	private SSHManager sshm;
	
	private UIManager uim = new UIManager( this );
	
	private MonitorManager mm = new MonitorManager();
	
	private ApplicationConstantsDocumentHandler doc;
	
	public ApplicationState() {
		//Do this, or all charts break... seriously
		ChartUtils.onceTimeFormat();
		
		doc = new ApplicationConstantsDocumentHandler( new ApplicationConstants( this ) );
		doc.loadDoc();
		
		LAFUtils.setAllLAFFonts( new Font( "Tahoma", Font.PLAIN, 11 ) );
		
		//setup internal frames to use my colors that were just loaded
		LAFUtils.setAllLAFInternalFrames( AC.FOREGROUND.darker(), AC.BACKGROUND, 35, 21, 21 );  //AC.FOREGROUND_DARKER is too dark for this
		
		//create (but don't start) all ssh processes
		sshm = new SSHManager( this );
		
		
		//MODULE STUFF
		//init all modules
		manager.getAllModules().forEach( m -> m.init() );
		
		//load all windowDefinitions
		manager.getModulesBySPIType( SPIWindowDefinitionProvider.class ).forEach( m -> m.loadWindowDefinitions() ); //same as above... colors aren't properly set if not loaded after loading doc
		
		WindowManager.addWindowDefinition( new SSHSessionDefinition() );
		WindowManager.addWindowDefinition( new OutputLogDefinition() );
		
		//add all data to the data map
		manager.getModulesBySPIType( SPIMonitorDataProvider.class ).forEach( m -> m.getMonitorData().forEach( d -> mm.getData().put( d.getClass().getName(), d ) ) );
		
		//init and add all data monitors to monitor map
		manager.getModulesBySPIType( SPIDataMonitorProvider.class ).forEach( m -> { 
			m.initDataMonitors( this, sshm.getSSHSession( AC.SSH_MASTER_PROCESS_NAME ) );
			m.getDataMonitors().forEach( d -> mm.getMonitors().put( d.getClass().getName(), d ) );
		} );
		
		//init and add all RT data monitors to monitor map
		manager.getModulesBySPIType( SPIRealTimeMonitorProvider.class ).forEach( m -> { 
			m.initRTDataMonitors( this, sshm.getSSHSession( AC.SSH_RT_PROCESS_NAME ) );
			m.getRTDataMonitors().forEach( d -> mm.getMonitors().put( d.getClass().getName(), d ) );
		} );
		//END MODULE STUFF

		new WarningMonitor( this );
		
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if ( AC.FRAME_SCREEN != null ) {
			GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			for ( GraphicsDevice d : screens ) {
				if ( d.getIDstring().equals( AC.FRAME_SCREEN ) ) {
					device = d;
					break;
				}
			}
		}
		
		uim.init( this, device );
		
		//auto start stuff
		if ( AC.AUTO ) {
			sshm.connectAllSessions();
			try {
				Thread.sleep( 1000 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
			mm.startAllMonitors();
		}
	}
	
	public FNMModuleManager getModuleManager() {
		return manager;
	}
	
	public void writeSettings() {
		doc.createDoc();
	}
	
	public UIManager getUIManager() {
		return uim;
	}
	
	public SSHManager getSSHManager() {
		return sshm;
	}
	
	public MonitorManager getMonitorManager() {
		return mm;
	}
}