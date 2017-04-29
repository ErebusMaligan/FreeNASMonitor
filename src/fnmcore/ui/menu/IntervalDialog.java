package fnmcore.ui.menu;

import java.awt.BorderLayout;
import java.awt.MouseInfo;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import fnmcore.constants.ApplicationConstants;
import gui.dialog.OKCancelDialog;
import gui.entry.Entry;
import gui.props.variable.LongVariable;
import state.provider.ApplicationProvider;
import statics.GU;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 10, 2015, 12:58:50 AM 
 */
public class IntervalDialog extends OKCancelDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationProvider state;

	private LongVariable disk = new LongVariable( ApplicationConstants.DISK_INTERVAL );
	
	private LongVariable cpu = new LongVariable( ApplicationConstants.CPU_INTERVAL );
	
	private LongVariable net = new LongVariable( ApplicationConstants.NET_INTERVAL );
	
	private LongVariable rt = new LongVariable( ApplicationConstants.REAL_TIME_DISK_INTERVAL );
	
	public IntervalDialog( ApplicationProvider state ) {
		super( state.getFrame(), ApplicationConstants.SET_INTERVAL_DIALOG, true );
		this.setLocation( MouseInfo.getPointerInfo().getLocation() );
		this.state = state;
		this.setLayout( new BorderLayout() );
		
		JPanel c = new JPanel();
		c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
		c.add( new Entry( ApplicationConstants.SET_DISK_INTERVAL, disk ) );
		GU.spacer( c );
		c.add( new Entry( ApplicationConstants.SET_CPU_INTERVAL, cpu ) );
		GU.spacer( c );
		c.add( new Entry( ApplicationConstants.SET_NET_INTERVAL, net ) );
		GU.spacer( c );
		c.add( new Entry( ApplicationConstants.SET_RT_INTERVAL, rt ) );
		
		this.add( c, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
		UIUtils.setColorsRecursive( this );
		
		this.pack();
	}
	
	public void ok() {
		ApplicationConstants.DISK_INTERVAL = (Long)disk.getValue();
		ApplicationConstants.CPU_INTERVAL = (Long)cpu.getValue();
		ApplicationConstants.NET_INTERVAL = (Long)net.getValue();
		ApplicationConstants.REAL_TIME_DISK_INTERVAL = (Long)rt.getValue();
		state.writeSettings();
		super.ok();
	}
}