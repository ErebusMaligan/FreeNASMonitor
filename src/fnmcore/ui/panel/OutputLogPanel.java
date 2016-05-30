package fnmcore.ui.panel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import statics.UIUtils;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import fnmcore.state.control.BroadcastEvent;
import fnmcore.state.control.BroadcastListener;
import gui.textarea.JTextAreaLineLimitDocument;
import gui.windowmanager.WindowClosedHook;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 15, 2015, 11:06:24 PM 
 */
public class OutputLogPanel extends JPanel implements WindowClosedHook, BroadcastListener {

	private static final long serialVersionUID = 1L;

	private JTextArea area = new JTextArea( 24, 100 );

	private ApplicationState state;

	public OutputLogPanel( ApplicationState state ) {
		this.state = state;
		this.setLayout( new BorderLayout() );
		int b = 10;
		area.setBorder( BorderFactory.createEmptyBorder( b, b, b, b ) );
		this.add( UIUtils.setJScrollPane( new JScrollPane( area ) ), BorderLayout.CENTER );
		area.setBackground( AC.BACKGROUND );
		area.setForeground( AC.FOREGROUND );
		area.setDocument( new JTextAreaLineLimitDocument( area, 100 ) );
		state.getUIManager().getOutput().registerArea( area );
		area.setEditable( false );
		state.getUIManager().addBroadcastListener( this );
	}

	@Override
	public void closed() {
		state.getUIManager().getOutput().removeArea( area );
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.EVENT_TYPE.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.EVENT_SETTING.ON ) {
				UIUtils.setColorsRecursive( this );
			} else {
				UIUtils.setColorsRecursiveOff( this );
			}
		}
	}
}