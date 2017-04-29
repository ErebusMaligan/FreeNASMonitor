package modules.cpu.ui.panel.charts;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import fnmcore.util.ChartUtils;
import modules.cpu.module.CPUModule;
import modules.cpu.state.data.CPUData;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 6:09:38 PM 
 */
public class CPUChartPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabs = new JTabbedPane();
	
	protected ApplicationProvider state;
	
	protected boolean init = false;
	
	public CPUChartPanel( ApplicationProvider state ) {
		this.state = state;
		UIUtils.setColors( this, tabs );
		UIUtils.setTabUI( tabs );
		ChartUtils.onceTimeFormat();
		this.setLayout( new BorderLayout() );
//		tabs.add( "Simple", new SimpleCPUChartPanel( state ) );
		tabs.add( "Usage", new AllCPUUsageChartPanel( state ) );
		tabs.add( "Combined Temp", new AllCPUTemperatureChartPanel( state ) );
		this.add( tabs, BorderLayout.CENTER );
		state.getMonitorManager().getMonitorByName( CPUModule.CPU_MONITOR ).addObserver( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		if ( !init ) {
			JPanel p = new JPanel();
			p.setLayout( new GridLayout( 0, 2 ) );
			for ( int i = 0; i < ( (CPUData)state.getMonitorManager().getDataByName( CPUModule.CPU_DATA ) ).getCpuCount(); i++ ) {
				p.add( new SingleCPUChartPanel( state, i ) );
			}
			tabs.add( "Individual Temp", UIUtils.setJScrollPane( new JScrollPane( p ) ) );
			init = true;
		}
	}
}