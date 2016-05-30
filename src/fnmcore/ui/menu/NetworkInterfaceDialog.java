package fnmcore.ui.menu;

import java.awt.BorderLayout;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.dialog.OKCancelDialog;
import gui.entry.Entry;
import gui.props.variable.StringVariable;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 10, 2015, 12:58:50 AM 
 */
public class NetworkInterfaceDialog extends OKCancelDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationState state;

	private StringVariable net = new StringVariable( AC.NI_NAME );
	
	public NetworkInterfaceDialog( ApplicationState state ) {
		super( state.getUIManager().getFrame(), AC.SET_NET_INTERFACE_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		c.add( new Entry( AC.SET_NET_INTERFACE_NAME, net ) );
		
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		UIUtils.setColorsRecursive( this );
		
		this.pack();
	}
	
	public void ok() {
		AC.NI_NAME = net.toString();
		state.writeSettings();
		super.ok();
	}
}