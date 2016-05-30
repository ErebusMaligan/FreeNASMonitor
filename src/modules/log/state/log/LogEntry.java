package modules.log.state.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: May 3, 2015, 1:15:16 AM 
 */
public class LogEntry {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	
	public static enum Type { WARNING, ERROR, INFO };
	
	protected boolean read = false;
	
	protected Type type;
	
	protected Date date;
	
	protected String message;
	
	protected String name;
	
	public LogEntry( Type type, String name, String message ) {
		this.type = type;
		this.message = message;
		this.name = name;
		date = new Date();
	}
	
	public void ack() {
		read = true;
	}
	
	public boolean isRead() {
		return read;
	}
	
	public synchronized String getTime() {
		return sdf.format( date );
	}
	
	public String getMessage() {
		return message;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public static LogEntry createWarning( String name, String message ) {
		return new LogEntry( Type.WARNING, name, message );
	}
}