package modules.mem.ui.panel.mem.charts;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JProgressBar;

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.progress.DigitalJProgressBar;
import gui.progress.EnhancedJProgressBar;
import listeners.BasicObservable;
import modules.mem.module.MemModule;
import modules.mem.state.mem.data.CurrentMemData;
import modules.mem.state.mem.data.MemData;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 30, 2015, 12:06:12 AM 
 */
public class SimpleMemoryChart extends SimpleChart implements BroadcastListener {

	private static final long serialVersionUID = 1L;

	private EnhancedJProgressBar a, i, w, l, f;
	
	private boolean lightsOff = false;
	
	public SimpleMemoryChart( ApplicationProvider state ) {
		super( state, state.getMonitorManager().getMonitorByName( MemModule.MEM_MONITOR ) );
		int max = ( (MemData)state.getMonitorManager().getDataByName( MemModule.MEM_DATA ) ).getRealMemory();
		CurrentMemData cm = ( (MemData)state.getMonitorManager().getDataByName( MemModule.MEM_DATA ) ).getCurrentMem();
		center.setLayout( new BoxLayout( center, BoxLayout.X_AXIS ) );
		dim = new Dimension( 15, 80 );
		width = 40;
		height = 18;
		a = addJProgressBar( 0, max, cm.an, cm.au );
		i = addJProgressBar( 0, max, cm.in, cm.iu );
		l = addJProgressBar( 0, max, cm.ln, cm.lu );
		w = addJProgressBar( 0, max, cm.wn, cm.wu );
		f = addJProgressBar( 0, max, cm.fn, cm.fu );
	}
	
	@Override
	protected EnhancedJProgressBar addJProgressBar( int min, int max, String name, String units ) {
		return addJProgressBar( new DigitalJProgressBar( JProgressBar.VERTICAL, min, max ), min, max, name, units );
	}
	
	@Override
	protected void calculateSections( EnhancedJProgressBar bar ) {
		try {
			bar.setSectionTwo( lightsOff ? UIUtils.lightsOff( ApplicationConstants.PROGRESS_BAR_MID, ApplicationConstants.LIGHTS_OFF ) : ApplicationConstants.PROGRESS_BAR_MID, (int)( bar.getMaximum() * .65 ) );
			bar.setSectionThree( lightsOff ? UIUtils.lightsOff( ApplicationConstants.PROGRESS_BAR_END, ApplicationConstants.LIGHTS_OFF ) : ApplicationConstants.PROGRESS_BAR_END, (int)( bar.getMaximum() * .9 ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		CurrentMemData cm = ( (MemData)state.getMonitorManager().getDataByName( MemModule.MEM_DATA ) ).getCurrentMem();
		a.setValue( cm.a );
		i.setValue( cm.i );
		l.setValue( cm.l );
		w.setValue( cm.w );
		f.setValue( cm.f );
	}

	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				UIUtils.setColorsRecursive( this );
				lightsOff = false;
			} else {
				lightsOff = true;
				UIUtils.setColorsRecursiveOff( this );
			}
			calculateSections( a );
			calculateSections( i );
			calculateSections( l );
			calculateSections( w );
			calculateSections( f );
		}
	}
}