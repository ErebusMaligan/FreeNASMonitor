package modules.cpu.ui.panel.charts;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import statics.UIUtils;
import fnmcore.state.ApplicationState;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 9:01:00 AM 
 */
public class IndividualCPUTemperatureChartPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	
	protected boolean init = false;
	
	private ApplicationState state;
	
	public IndividualCPUTemperatureChartPanel( ApplicationState state ) {
		this.state = state;
		UIUtils.setColors( this );
		this.setLayout( new GridLayout( 0, 2 ) );
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		if ( !init ) {
			for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
				this.add( new SingleCPUChartPanel( state, i ) );
			}
			init = true;
		}
	}
}