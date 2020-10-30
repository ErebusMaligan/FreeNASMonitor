package modules.disk.ui.table.info;

import java.util.ArrayList;
import java.util.Collections;
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
import gui.table.sorter.CustomizableTableSorter;
import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.SmartInfo;
import modules.disk.ui.table.info.renderer.RuntimeRenderer;
import modules.disk.ui.table.info.renderer.SmartInfoRenderer;
import modules.disk.ui.table.info.renderer.TemperatureRenderer;
import modules.disk.ui.table.info.renderer.TestResultRenderer;
import state.control.BroadcastEvent;
import state.control.BroadcastListener;
import state.provider.ApplicationProvider;
import statics.UIUtils;
import ui.theme.ThemeConstants;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 4:12:55 AM 
 */
public class DiskTable extends AbstractTableImplementation implements Observer, BroadcastListener {

	private ApplicationProvider state;
	
	private TableCellRenderer cell; 
	
	private TableCellRenderer result;
	
	private TableCellRenderer temp;
	
	private TableCellRenderer run;
	
	private boolean lightsOff = false;
	
	public DiskTable( ApplicationProvider state ) {
		this.state = state;
		sorter = new DiskTableSorter();
		cell = new SmartInfoRenderer( state );
		run = new RuntimeRenderer( state );
		temp = new TemperatureRenderer( state );
		result = new TestResultRenderer( state );
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
		SmartInfo si = (SmartInfo)data.get( i );
		List<Object> values = new ArrayList<>();
		for ( Object o : getColumnNames() ) {
			values.add( si.getValue( o ) );
		}
		return values.toArray(); 
	}

	@Override
	protected Object[] getColumnNames() {
		return SmartInfo.orderedNames;
	}

	@Override
	protected void pullSpecificData() {
		if ( data == null ) {
			data = new Vector<>();
		}
		data.clear();
		for ( String l : ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getLocations() ) {
			SmartInfo si = ( (DiskData)state.getMonitorManager().getDataByName( DiskModule.DISK_DATA ) ).getInfo( l );
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
			if ( table.getColumnModel().getColumn( i ).getHeaderValue().equals( SmartInfo.RESULT ) ) {
				col.setCellRenderer( result );
			} else if ( table.getColumnModel().getColumn( i ).getHeaderValue().equals( SmartInfo.TEMP ) ) {
				col.setCellRenderer( temp );
			} else if ( table.getColumnModel().getColumn( i ).getHeaderValue().equals( SmartInfo.RUN ) ) {
				col.setCellRenderer( run );
			} else {
				col.setCellRenderer( cell );
			}
		}
	}
	
	@Override
	protected String getIDFromDataObject( Object[] data ) {
		return (String)data[ 1 ];
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
	
	private class DiskTableSorter extends CustomizableTableSorter {
		@Override
		protected void specificSort() {
			ArrayList<Integer> intKeys = null;
			try {
				intKeys = new ArrayList<Integer>();
				for ( String s : keys ) {
					intKeys.add( Integer.parseInt( s ) );
				}
			} catch (NumberFormatException e ) {
				intKeys = null;
			}
			if ( intKeys != null ) {
				Collections.sort( intKeys );
				keys = new ArrayList<String>();
				for ( Integer i : intKeys ) {
					keys.add( String.valueOf( i ) );
				}
			} else {
				ArrayList<Double> doubleKeys = null;
				try {
					doubleKeys = new ArrayList<Double>();
					for ( String s : keys ) {
						doubleKeys.add( Double.parseDouble( s ) );
					}
				} catch (NumberFormatException e ) {
					doubleKeys = null;
				}
				if ( doubleKeys != null ) {
					Collections.sort( doubleKeys );
					keys = new ArrayList<String>();
					for ( Double d : doubleKeys ) {
						keys.add( String.valueOf( d ) );
					}
				} else {
					Collections.sort( keys );
				}
			}
		}
	}
}