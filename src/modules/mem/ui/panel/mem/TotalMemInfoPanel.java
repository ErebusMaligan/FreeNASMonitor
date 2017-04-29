package modules.mem.ui.panel.mem;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modules.mem.state.mem.data.MemData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import statics.GU;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 29, 2015, 8:40:02 PM 
 */
public class TotalMemInfoPanel extends JPanel implements BroadcastListener {

	private static final long serialVersionUID = 1L;
	
	public TotalMemInfoPanel( MemData d ) {
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		this.add( Box.createGlue() );
		this.add( new JLabel( "Real: " + d.getRealMemory() + " MB", JLabel.CENTER ) );
		GU.spacer( this );		
		this.add( new JLabel( "Avail: " + d.getAvailableMemory() + " MB", JLabel.CENTER ) );
		this.add( Box.createGlue() );
		UIUtils.setColorsRecursive( this );
		UIUtils.setColors( this );
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				UIUtils.setColorsRecursive( this );
			} else {
				UIUtils.setColorsRecursiveOff( this );
			}
		}
	}
}