package fnmcore.state.control;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 3, 2015, 10:25:44 PM 
 */
public class BroadcastEvent {

	private Broadcaster source;
	
	private Class<?> destination;
	
	private EVENT_TYPE eventType;
	
	private EVENT_SETTING eventSetting;
	
	private Object additional;
	
	public enum EVENT_TYPE { LIGHT_STATUS, MONITOR_COMMAND, MONITOR_STATE, SSH_SESSION_COMMAND, SSH_SESSION_STATE };
	
	public enum EVENT_SETTING { OFF, ON, START, STOP, CONNECT, DISCONNECT };
	
	public BroadcastEvent( Broadcaster source, EVENT_TYPE eventType, EVENT_SETTING eventSetting ) {
		this( source, null, eventType, eventSetting );
	}
	
	public BroadcastEvent( Broadcaster source, EVENT_TYPE eventType, EVENT_SETTING eventSetting, Object additional ) {
		this( source, null, eventType, eventSetting, additional );
	}
	
	public BroadcastEvent( Broadcaster source, Class<?> destination, EVENT_TYPE eventType, EVENT_SETTING eventSetting ) {
		this( source, destination, eventType, eventSetting, null );
	}
	
	public BroadcastEvent( Broadcaster source, Class<?> destination, EVENT_TYPE eventType, EVENT_SETTING eventSetting, Object additional ) {
		this.source = source;
		this.eventType = eventType;
		this.eventSetting = eventSetting;
		this.destination = destination;
		this.additional = additional;
	}

	public Class<?> getDestination() {
		return destination;
	}
	
	public Broadcaster getSource() {
		return source;
	}

	public EVENT_TYPE getEventType() {
		return eventType;
	}

	public EVENT_SETTING getEventSetting() {
		return eventSetting;
	}
	
	public Object getAdditional() {
		return additional;
	}
	
	@Override
	public String toString() {
		return "source: " + source.getClass().getName() + 
				" destinaton: " + ( destination == null ? "ALL" : destination.getName() ) + 
				" |    " + eventType.toString() + 
				" (" + eventSetting.toString() + ")" + 
				( additional == null ? "" : "    |    " + additional.toString() ) ; 
	}
}