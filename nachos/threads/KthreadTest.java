package nachos.threads;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class KthreadTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJoin() {
		//fail("Not yet implemented");
		KThread a = new KThread(new Runnable() {
		    public void run() { 
		    	for (int i=0;i<5;i++){
		    		System.out.println("Printing i:"+i);
		    	}
		    }
		});
		
		a.fork();
		
		System.out.println("Main Thread");
		a.join();
		System.out.println("Main Thread Finishing");
		
		
	}

}
