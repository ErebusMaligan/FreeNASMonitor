package modules.disk.ui.panel.charts;

import java.awt.Dimension;
import java.util.Observable;

import fnmcore.constants.ApplicationConstants;
import fnmcore.ui.panel.generic.charts.SimpleChart;
import gui.progress.EnhancedJProgressBar;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DFData;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.DiskIOInfo;
import modules.disk.state.data.RealtimeDiskData;
import modules.disk.state.data.SmartInfo;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 8, 2015, 11:29:44 PM 
 */
public class DiskIOChart extends SimpleChart implements BroadcastListener {

	private static final long serialVersionUID = 1L;

	private EnhancedJProgressBar temp, busy, rkbps, wkbps, used;

	private String nm;
	
	private String pool;
	
	private boolean updatedPool = false;
	
	private DiskIOChartHolder holder;
	
	private boolean lightsOff = false;
	
	private int updateCount = 0;
	
	public DiskIOChart( DiskIOChartHolder holder, String nm, ApplicationProvider state ) {
		super( state, state.getMonitorManager().getMonitorByName( DiskModule.RT_DISK_MONITOR ) );
		this.holder = holder;
		this.nm = nm;
		dim = new Dimension( 75, dim.height );
		height = 12;
		temp = addJProgressBar( 28, 70, "Temp", "(" + ApplicationConstants.DEGREE + "C)" );
//		GU.spacer( center, new Dimension( 10, 12 ) );
		busy = addJProgressBar( 0, 100, "Busy", "%" );
		rkbps = addJProgressBar( 0, 100, "Read", "kbps" );
		wkbps = addJProgressBar( 0, 100, "Write", "kbps" );
		used = addJProgressBar( 0, 100, "Full", "%" );
		setLabel( nm );
	}
	
	public void updateMax( int maxR, int maxW ) {
		rkbps.setMaximum( maxR );
		wkbps.setMaximum( maxW );
		calculateSections( rkbps );
		calculateSections( wkbps );
	}
	
	public String getPool() {
		return pool;
	}

	public String getFullName() {
		return "[" + pool + "] " + nm;
	}
	
	public String getNM() {
		return nm;
	}
	
	@Override
	public void update( Observable o, Object arg ) {
		if ( updateCount < 5 ) {
			updateCount++;
		}
		DiskIOInfo i = ( (RealtimeDiskData)state.getMonitorManager().getDataByName( DiskModule.RT_DISK_DATA ) ).getIOInfo().get( nm );
		busy.setValue( new Float( i.busy  ).intValue() );
		int r = new Float( i.rkbps ).intValue();
		int w = new Float( i.wkbps ).intValue();
		int max = r > w ? r : w;
		max = max + ( max / 10 );
		rkbps.setValue( r );
		wkbps.setValue( w );
		
		SmartInfo s = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( nm );
		if ( s != null ) {
			temp.setValue( Integer.parseInt( s.temp ) );
		} else {
			temp.setValue( 0 );
		}
		

		pool = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getPool( nm );
		if ( pool != null ) {
			if ( !updatedPool ) {
				if ( pool != null && pool.contains( "-boot" ) ) {  //if it turns out this was the boot drive, remove this panel
					if ( this.getParent() != null ) {
						holder.removeAndSort( this, false );
					}
				} else {
					n.setText( "[" + pool + "] " + nm );
					if ( this.getParent() != null ) {
						holder.removeAndSort( this, true );
					}
				}
				updatedPool = true;
			}
		}
		//if updates were received and this pool is still null - then it is a disk not in a pool, remove it from the panel
		//TODO: 2020 not sure if this is ideal - may still want to see disks not in pools
		if ( pool == null && updateCount == 5 ) {
			if ( this.getParent() != null ) {
				holder.removeAndSort( this, false );
			}
		}
		
		if ( updatedPool ) {
			DFData d = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getDiskUsage( pool );
			if ( d != null ) {
				used.setValue( d.percent );
				if ( d.percent > 82 && d.percent < 90 ) {
					n.setForeground( ThemeConstants.FOREGROUND );
					n.setBackground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND_DARKER, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND_DARKER );
					n.setOpaque( true );
				} else if ( d.percent >= 90 ) {
					n.setForeground( ThemeConstants.BACKGROUND );
					n.setBackground( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND );
					n.setOpaque( true );
				}
			}
		}
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
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				UIUtils.setColorsRecursive( this );
				lightsOff = false;
			} else {
				lightsOff = true;
				UIUtils.setColorsRecursiveOff( this );
			}
			calculateSections( temp );
			calculateSections( busy );
			calculateSections( rkbps );
			calculateSections( wkbps );
			calculateSections( used );
		}
	}
}