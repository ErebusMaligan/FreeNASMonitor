package fnmcore.app;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: May 1, 2016, 6:12:10 PM 
 */
public class TestApplication {
	
	public TestApplication() {
		A a = new A();
		B b = new B();
		System.out.println( I.class.isInstance( a ) );
		System.out.println( I.class.isInstance( b ) );
		System.out.println( I2.class.isInstance( a ) );
		System.out.println( I2.class.isInstance( b ) );
		
	}
	
	private interface I {
		
	}
	
	private class A implements I {
		
	}
	
	private interface I2 extends I {
		
	}
	
	private class B implements I2 {
		
	}

	public static void main( String[] args ) {
		new TestApplication();
	}	
}