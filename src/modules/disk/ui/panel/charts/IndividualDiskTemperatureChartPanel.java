package modules.disk.ui.panel.charts;

import java.awt.GridLayout;

import javax.swing.JPanel;

import listeners.BasicObservable;
import listeners.BasicObserver;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import state.provider.ApplicationProvider;
import statics.UIUtils;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Oct 13, 2015, 9:20:55 AM 
 */
public class IndividualDiskTemperatureChartPanel extends JPanel implements BasicObserver {

	private static final long serialVersionUID = 1L;
	
	protected boolean init = false;
	
	private ApplicationProvider state;

	public IndividualDiskTemperatureChartPanel( ApplicationProvider state ) {
		this.state = state;
		UIUtils.setColors( this );
		this.setLayout( new GridLayout( 0, 2 ) );
		state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ).addObserver( this );
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( !init ) {
			for ( String loc : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
				if ( ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( loc ) != null ) {
					this.add( new SingleDiskChartPanel( state, loc ) );
				}
			}
			init = true;
		}
	}
}