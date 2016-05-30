package fnmcore.ui.menu;

import java.awt.BorderLayout;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import statics.GU;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.dialog.OKCancelDialog;
import gui.entry.CheckEntry;
import gui.entry.DirectoryEntry;
import gui.entry.Entry;
import gui.entry.FileEntry;
import gui.entry.PasswordEntry;
import gui.props.variable.BooleanVariable;
import gui.props.variable.StringVariable;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 10, 2015, 12:58:50 AM 
 */
public class SSHDialog extends OKCancelDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationState state;

	private StringVariable ip = new StringVariable( AC.SSH_IP);
	private StringVariable user = new StringVariable( AC.SSH_USER );
	private StringVariable pw = new StringVariable( AC.SSH_PW );
	private StringVariable path = new StringVariable( AC.SSH_APP_PATH );
	private BooleanVariable useKey = new BooleanVariable( AC.SSH_USE_KEY );
	private StringVariable keyPath = new StringVariable( AC.SSH_KEY_PATH );
	private StringVariable port = new StringVariable( AC.SSH_PORT );
	
	public SSHDialog( ApplicationState state ) {
		super( state.getUIManager().getFrame(), AC.SET_SSH_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_SSH_IP, ip, GU.SHORT ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_SSH_PORT, port, GU.SHORT ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_SSH_USER, user, GU.SHORT ) );
		GU.spacer( c );
		c.add( new PasswordEntry( AC.SET_SSH_PASS, pw, GU.SHORT ) );
		GU.spacer( c );
		c.add( new DirectoryEntry( AC.SET_SSH_PATH, path ) );
		GU.spacer( c );
		c.add( new CheckEntry( AC.SET_USE_SSH_KEY, useKey ) );
		GU.spacer( c );
		c.add( new FileEntry( AC.SET_SSH_KEY_PATH, keyPath, new FileNameExtensionFilter( "private keys", "ppk" ) ) );
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		UIUtils.setColorsRecursive( this );
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		this.pack();
	}
	
	public void ok() {
		AC.SSH_IP = ip.toString();
		AC.SSH_USER = user.toString();
		AC.SSH_PW = pw.toString();
		AC.SSH_APP_PATH = path.toString();
		AC.SSH_USE_KEY = Boolean.parseBoolean( useKey.toString() );
		AC.SSH_KEY_PATH = keyPath.toString();
		AC.SSH_PORT = port.toString();
		state.writeSettings();
		super.ok();
	}
}