package fnmcore.app;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import static fnmcore.constants.ApplicationConstants.*;
import fnmcore.constants.ApplicationConstants;
import fnmcore.constants.ApplicationConstantsDocumentHandler;
import fnmcore.constants.ColorSettings;
import fnmcore.constants.IntervalSettings;
import fnmcore.constants.NetSettings;
import fnmcore.ui.MonitorFrame;
import fnmcore.util.ChartUtils;
import gui.windowmanager.WindowManagerConstants;
import module.AppModule;
import module.spi.SPIDataMonitorProvider;
import module.spi.SPIRealTimeMonitorProvider;
import modules.control.module.ControlModule;
import modules.cpu.module.CPUModule;
import modules.disk.module.DiskModule;
import modules.mem.module.MemModule;
import modules.net.module.NetModule;
import modules.sensor.module.SensorModule;
import modules.system.module.SysModule;
import ssh.SSHConstants;
import ssh.SSHSettings;
import state.provider.ApplicationProvider;
import state.provider.AutoSettings;
import state.provider.DebugSettings;
import state.provider.ProviderConstants;
import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;
import ui.theme.ThemeConstants;
import ui.window.WindowConstants;
import ui.window.WindowSettings;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 7:48:27 PM 
 */
public class FreeNASMonitor extends ApplicationProvider implements XMLValues {
	
	private AutoSettings autoS;

	private NetSettings netS;
	
	private DebugSettings debugS;
	
	private IntervalSettings intS;
	
	private WindowSettings winS;
	
	private SSHSettings sshS;
	
	private ColorSettings colorS;
	
	@Override 
	protected void initOtherSPI() {
		//init and add all data monitors to monitor map
		manager.getModulesBySPIType( SPIDataMonitorProvider.class ).forEach( m -> { 
			m.initDataMonitors( mm, this, sshm.getSSHSession( ApplicationConstants.SSH_MASTER_PROCESS_NAME ) );
			m.getDataMonitors().forEach( d -> mm.getMonitors().put( d.getClass().getName(), d ) );
		} );
		
		//init and add all RT data monitors to monitor map
		manager.getModulesBySPIType( SPIRealTimeMonitorProvider.class ).forEach( m -> { 
			m.initRTDataMonitors( mm, this, sshm.getSSHSession( ApplicationConstants.SSH_RT_PROCESS_NAME ) );
			m.getRTDataMonitors().forEach( d -> mm.getMonitors().put( d.getClass().getName(), d ) );
		} );
	}

	@Override
	protected void init() {
		WindowConstants.APP_ICON = new ImageIcon( ApplicationConstants.class.getResource( "freenas.png" ) );
		WindowConstants.FRAME_TITLE = "FreeNAS Monitor";
		
		ThemeConstants.BACKGROUND = Color.BLACK;
		ThemeConstants.FOREGROUND = Color.RED;
		ThemeConstants.FOREGROUND_DARKER = ThemeConstants.FOREGROUND.darker().darker().darker().darker();
		ThemeConstants.TRANSPARENT_BG = new Color( ThemeConstants.BACKGROUND.getRed(), ThemeConstants.BACKGROUND.getGreen(), ThemeConstants.BACKGROUND.getBlue(), 96 );
		
		SSHConstants.SSH_SESSION_IDS = new String[] { SSH_MASTER_PROCESS_NAME, SSH_RT_PROCESS_NAME };
		super.init();
		//Do this, or all charts break... seriously
		ChartUtils.onceTimeFormat();
		
		autoS = new AutoSettings();
		netS = new NetSettings();
		debugS = new DebugSettings();
		intS = new IntervalSettings();
		winS = new WindowSettings();
		sshS = new SSHSettings();
		colorS = new ColorSettings();
		
		doc = new ApplicationConstantsDocumentHandler( this );
	}
	
	@Override
	protected void initFrame() {
		frame = new MonitorFrame( this, determineMonitor() );
	}

	@Override
	protected List<AppModule> getModuleList() {
		return Arrays.asList( new ControlModule(), new CPUModule(), new DiskModule(), new MemModule(), new NetModule(), new SysModule(), new SensorModule() );
	}

	@Override
	public List<XMLValues> getChildNodes() { 
		return Arrays.asList( new XMLValues[] { debugS, autoS, netS, intS, winS, sshS, colorS, tab } ); 
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
		if ( e.getChild( ProviderConstants.XAUTO ) != null ) {
			autoS.loadParamsFromXMLValues( e.getChild( ProviderConstants.XAUTO ) );
		}
		if ( e.getChild( ProviderConstants.XDEBUG ) != null ) {
			debugS.loadParamsFromXMLValues( e.getChild( ProviderConstants.XDEBUG ) );
		}
		if ( e.getChild( XINT ) != null ) {
			intS.loadParamsFromXMLValues( e.getChild( XINT ) );
		}
		if ( e.getChild( WindowConstants.XWINDOW ) != null ) {
			winS.loadParamsFromXMLValues( e.getChild( WindowConstants.XWINDOW ) );
		}
		if ( e.getChild( SSHConstants.XSSH ) != null ) {
			sshS.loadParamsFromXMLValues( e.getChild( SSHConstants.XSSH ) );
		}
		if ( e.getChild( ThemeConstants.XCOLORS ) != null ) {
			colorS.loadParamsFromXMLValues( e.getChild( ThemeConstants.XCOLORS ) );
		}
		if ( e.getChild( WindowManagerConstants.XTABS ) != null ) {
			tab.loadParamsFromXMLValues( e.getChild( WindowManagerConstants.XTABS ) );
		}
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<>();
		ret.put( XSETTINGS, values );
		return ret;
	}
	
	
	public static void main( String[] args ) {
		TerminalWindowManager.getInstance().OS = System.getProperty( "os.name" ).contains( "Win" ) ? OSTerminalSettings.WINDOWS : OSTerminalSettings.LINUX;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        System.out.println( info.getName() );
				if ( info.getName().contains( "Nimbus" ) ) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
//			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
//			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel" );
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}
		
//		for ( Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts() ) {
//			System.out.println( f.getFontName() );
//		}
		new FreeNASMonitor();
	}
}