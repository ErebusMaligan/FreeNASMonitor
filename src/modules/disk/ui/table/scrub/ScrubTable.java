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

import modules.disk.module.DiskModule;
import modules.disk.state.data.DiskData;
import modules.disk.state.data.ScrubInfo;
import modules.disk.ui.table.scrub.renderer.ScrubInfoRenderer;
import fnmcore.constants.AC;
import fnmcore.state.ApplicationState;
import gui.table.AbstractTableImplementation;
import gui.table.renderer.DefaultHeaderCellRenderer;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 5, 2015, 12:40:14 AM 
 */
public class ScrubTable extends AbstractTableImplementation implements Observer {

	private TableCellRenderer cell = new ScrubInfoRenderer();
	
	private ApplicationState state;
	
	public ScrubTable( ApplicationState state ) {
		this.state = state;
		table.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		table.setGridColor( AC.FOREGROUND_DARKER );
		headerRenderer = new DefaultHeaderCellRenderer( this, AC.FOREGROUND, AC.BACKGROUND );
		( (DefaultHeaderCellRenderer)headerRenderer ).setGradientColor( AC.BACKGROUND );
		( (DefaultHeaderCellRenderer)headerRenderer ).setGradientBackgroundColor( AC.BACKGROUND );
		( (DefaultHeaderCellRenderer)headerRenderer ).setIconColors( AC.FOREGROUND, AC.FOREGROUND_DARKER );
		( (DefaultHeaderCellRenderer)headerRenderer ).setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, AC.FOREGROUND.darker().darker(), AC.FOREGROUND.darker().darker() ) );
		init( true );
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
}