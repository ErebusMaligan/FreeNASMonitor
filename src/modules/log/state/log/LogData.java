package modules.log.state.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import modules.log.state.log.LogEntry.Type;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 3, 2015, 2:38:02 AM 
 */
public class LogData extends Observable {
	
	private List<LogEntry> logs = new ArrayList<>();
	
	public List<LogEntry> getLogsByType( Type type ) {
		List<LogEntry> ret = new ArrayList<>();
		for ( LogEntry l : logs ) {
			if ( l.getType() == type ) {
				ret.add( l );
			}
		}
		return ret;
	}
	
	public void delete( LogEntry m ) {
		logs.remove( m );
	}
	
	public void add( LogEntry m ) {
		boolean exists = false;
		for ( LogEntry l : logs ) {
			if ( l.getName().equals( m.getName() ) ) {
				exists = true;
				break;
			}
		}
		if ( !exists ) {
			logs.add( m );
			setChanged();
			try {
				notifyObservers( m );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
}