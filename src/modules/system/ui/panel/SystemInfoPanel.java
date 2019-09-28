package modules.system.ui.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.entry.LabelEntry;
import gui.props.variable.IntVariable;
import gui.props.variable.StringVariable;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import modules.system.module.SysConstants;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 6:21:58 AM 
 */
public class SystemInfoPanel extends JPanel implements Observer, BroadcastListener {
	
	private static final long serialVersionUID = 1L;

	private StringVariable model = new StringVariable();
	
	private StringVariable sysTime = new StringVariable();
	
	private StringVariable upTime = new StringVariable();
	
	private StringVariable cpuTempSource = new StringVariable();
	
	private IntVariable cpuCount = new IntVariable();
	
	private StringVariable fnVer = new StringVariable();
	
	private ApplicationProvider state;
	
	public SystemInfoPanel( ApplicationProvider state ) {
		this.state = state;
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
		state.addBroadcastListener( this );
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		Dimension[] dim = new Dimension[] { new Dimension( 150, 18 ), new Dimension( 200, 18 ) };
		this.add( Box.createVerticalGlue() );
		this.add( new LabelEntry( SysConstants.SIP_SYSTIME, sysTime, dim ) );
		this.add( new LabelEntry( SysConstants.SIP_UPTIME, upTime, dim ) );
		this.add( new LabelEntry( SysConstants.SIP_MODEL, model, dim ) );
		this.add( new LabelEntry( SysConstants.SIP_CPU_COUNT, cpuCount, dim ) );
		this.add( new LabelEntry( SysConstants.SIP_SOURCE, cpuTempSource, dim ) );
		this.add( new LabelEntry( SysConstants.SIP_FN_VER, fnVer, dim ) );
		UIUtils.setColorsRecursive( this );
		this.add( Box.createVerticalGlue() );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		CPUData sd = ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) );
		model.fromString( sd.getModel() );
		sysTime.fromString( sd.getSysTime() );
		upTime.fromString( sd.getUpTime() );
		cpuTempSource.fromString( sd.getSource() );
		cpuCount.fromString( String.valueOf( sd.getCpuCount() ) );
		fnVer.fromString( sd.getVersion() );
		for ( Component c : this.getComponents() ) {
			if ( c instanceof LabelEntry ) {
				( (LabelEntry)c ).reload();
			}
		}
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
