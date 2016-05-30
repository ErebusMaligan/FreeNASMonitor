package modules.disk.state.data;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Apr 25, 2015, 5:36:00 PM 
 */
public class RuntimeData {
	
	public int years, days, hours;

	public RuntimeData( int years, int days, int hours ) {
		this.years = years;
		this.days = days;
		this.hours = hours;
	}
	
	@Override
	public String toString() {
		return years + " Y, " + days + " D, " + hours + " H";
	}
}