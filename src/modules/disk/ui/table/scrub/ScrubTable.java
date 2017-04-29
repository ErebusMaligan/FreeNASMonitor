package modules.disk.ui.table.scrub;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fnmcore.constants.ApplicationConstants;
import gui.table.AbstractTableImplementation;
import gui.table.renderer.DefaultHeaderCellRenderer;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
import modules.disk.ui.table.scrub.renderer.ScrubInfoRenderer;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 5, 2015, 12:40:14 AM 
 */
public class ScrubTable extends AbstractTableImplementation implements Observer, BroadcastListener {

	private TableCellRenderer cell;
	
	private ApplicationProvider state;
	
	private boolean lightsOff = false;
	
	public ScrubTable( ApplicationProvider state ) {
		this.state = state;
		cell = new ScrubInfoRenderer( state );
		table.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		table.setGridColor( ThemeConstants.FOREGROUND_DARKER );
		headerRenderer = new DefaultHeaderCellRenderer( this, ThemeConstants.FOREGROUND, ThemeConstants.BACKGROUND );
		setColors();
		init( true );
		state.addBroadcastListener( this );
	}
	
	private void setColors() {
		( (DefaultHeaderCellRenderer)headerRenderer ).setGradientColor( lightsOff ? UIUtils.lightsOff( ThemeConstants.BACKGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.BACKGROUND );
		( (DefaultHeaderCellRenderer)headerRenderer ).setGradientBackgroundColor( lightsOff ? UIUtils.lightsOff( ThemeConstants.BACKGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.BACKGROUND  );
		( (DefaultHeaderCellRenderer)headerRenderer ).setIconColors( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND, lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND_DARKER, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND_DARKER );
		( (DefaultHeaderCellRenderer)headerRenderer ).setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND.darker().darker(), ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND.darker().darker(), lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND.darker().darker(), ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND.darker().darker() ) );
		( (DefaultHeaderCellRenderer)headerRenderer ).setTextColor( lightsOff ? UIUtils.lightsOff( ThemeConstants.FOREGROUND, ApplicationConstants.LIGHTS_OFF ) : ThemeConstants.FOREGROUND );
		table.getTableHeader().repaint();
		table.repaint();
	}

	@Override
	protected void addAsListener() {
		state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ).addObserver( this );
	}
	
	@Override
	protected Object[] convertRow( int i ) {
		ScrubInfo si = (ScrubInfo)data.get( i );
		List<Object> values = new ArrayList<>();
		for ( Object o : getColumnNames() ) {
			Object v = si.getValue( o );
			values.add( v == null ? "" : v.toString() );
		}
		return values.toArray();
	}

	@Override
	protected Object[] getColumnNames() {
		return ScrubInfo.orderedNames;
	}

	@Override
	protected void pullSpecificData() {
		if ( data == null ) {
			data = new Vector<>();
		}
		data.clear();
		for ( String p : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getScrubs().keySet() ) {
			ScrubInfo si = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getScrubs().get( p );
			if ( si != null ) {
				data.add( si );
			}
		}
	}

	@Override
	public void shutdown() {
		super.shutdown();
		state.getMonitorManager().getMonitorByName( DiskModule.DISK_MONITOR ).deleteObserver( this );
	}

	@Override
	public void update( Observable o, Object arg ) {
		pullData();
	}
	
	@Override
	public void reconstructHook() {
		for ( int i = 0; i < table.getColumnCount(); i++ ) {
			TableColumn col = table.getColumnModel().getColumn( i );
				col.setCellRenderer( cell );
		}
	}
	
	@Override
	protected String getIDFromDataObject( Object[] data ) {
		return (String)data[ 0 ];
	}
	
	@Override
	public void broadcastReceived( BroadcastEvent e ) {
		if ( e.getEventType() == BroadcastEvent.LIGHT_STATUS ) {
			if ( e.getEventSetting() == BroadcastEvent.ON ) {
				lightsOff = false;
				setColors();
			} else {
				lightsOff = true;
				setColors();
			}
		}
	}
}