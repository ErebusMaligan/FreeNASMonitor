package fnmcore.app;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;
import fnmcore.state.ApplicationState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 7:48:27 PM 
 */
public class FreeNASMonitor {
	
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
		new ApplicationState();
	}
}