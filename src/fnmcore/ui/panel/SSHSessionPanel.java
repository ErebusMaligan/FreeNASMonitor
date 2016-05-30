package fnmcore.ui.panel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import statics.UIUtils;
import ui.terminal.panel.TerminalPanel;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.ShutdownComponent;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 24, 2015, 8:07:53 PM 
 */
public class SSHSessionPanel extends JPanel implements ShutdownComponent {
	
	private static final long serialVersionUID = 1L;
	
	private TerminalPanel term;
	
	public SSHSessionPanel( ApplicationState state, String processName ) {
		this.setLayout( new BorderLayout() );
		term = new TerminalPanel( processName, AC.SSH_APP_PATH );
		term.getWindowSettings().setBackgroundColor( AC.BACKGROUND );
		term.getWindowSettings().setForegroundColor( AC.FOREGROUND );
		term.getWindowSettings().setLineLimit( 4000 );
		term.getArea().setFont( term.getArea().getFont().deriveFont( 12.0f ).deriveFont( Font.BOLD ) );
		term.setColors();
		term.setLineLimit();
		UIUtils.setJScrollPane( term.getScrollPane() );
		term.getWindowSettings().setForegroundColor( AC.FOREGROUND );
		term.getWindowSettings().setBackgroundColor( AC.BACKGROUND );
		this.add( term, BorderLayout.CENTER );
		state.getUIManager().registerShutdownComponent( this );
	}
	
	public void sendCommand( String command ) {
		term.sendCommand( command );
	}

	@Override
	public void shutdown() {
		term.shutDown();
	}
}