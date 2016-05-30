package fnmcore.ui.menu;

import java.awt.BorderLayout;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import statics.GU;
import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.dialog.OKCancelDialog;
import gui.entry.Entry;
import gui.props.variable.LongVariable;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 10, 2015, 12:58:50 AM 
 */
public class IntervalDialog extends OKCancelDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationState state;

	private LongVariable disk = new LongVariable( AC.DISK_INTERVAL );
	
	private LongVariable cpu = new LongVariable( AC.CPU_INTERVAL );
	
	private LongVariable net = new LongVariable( AC.NET_INTERVAL );
	
	private LongVariable rt = new LongVariable( AC.REAL_TIME_DISK_INTERVAL );
	
	public IntervalDialog( ApplicationState state ) {
		super( state.getUIManager().getFrame(), AC.SET_INTERVAL_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		c.add( new Entry( AC.SET_DISK_INTERVAL, disk ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_CPU_INTERVAL, cpu ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_NET_INTERVAL, net ) );
		GU.spacer( c );
		c.add( new Entry( AC.SET_RT_INTERVAL, rt ) );
		
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		UIUtils.setColorsRecursive( this );
		
		this.pack();
	}
	
	public void ok() {
		AC.DISK_INTERVAL = (Long)disk.getValue();
		AC.CPU_INTERVAL = (Long)cpu.getValue();
		AC.NET_INTERVAL = (Long)net.getValue();
		AC.REAL_TIME_DISK_INTERVAL = (Long)rt.getValue();
		state.writeSettings();
		super.ok();
	}
}