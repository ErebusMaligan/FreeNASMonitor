package modules.mem.ui.panel.mem;

import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modules.mem.state.mem.data.PhysicalMemData;
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
public class PhysicalMemInfoPanel extends JPanel implements BroadcastListener {

	private static final long serialVersionUID = 1L;

	private int size = 0;

	public PhysicalMemInfoPanel( List<PhysicalMemData> data ) {
		this.setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
		data.forEach( d -> size += d.size );
		this.add( Box.createGlue() );
		Arrays.asList( new String[] { String.valueOf( data.size() ) + " DIMMs", String.valueOf( size ) + " MB", data.get( 0 ).type, data.get( 0 ).speed } )./*forEach( s -> labels.add( new JLabel( s, JLabel.CENTER ) ) );*/
		forEach( s -> { 
			this.add( new JLabel( s, JLabel.CENTER ) );
			GU.spacer( this );
		} );
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