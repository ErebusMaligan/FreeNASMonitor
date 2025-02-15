package modules.cpu.ui.panel.charts;

import java.awt.GridLayout;

import javax.swing.JPanel;

import listeners.BasicObservable;
import listeners.BasicObserver;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 9:01:00 AM 
 */
public class IndividualCPUTemperatureChartPanel extends JPanel implements BasicObserver {

	private static final long serialVersionUID = 1L;
	
	protected boolean init = false;
	
	private ApplicationProvider state;
	
	public IndividualCPUTemperatureChartPanel( ApplicationProvider state ) {
		this.state = state;
		UIUtils.setColors( this );
		this.setLayout( new GridLayout( 0, 2 ) );
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( !init ) {
			for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
				this.add( new SingleCPUChartPanel( state, i ) );
			}
			init = true;
		}
	}
}