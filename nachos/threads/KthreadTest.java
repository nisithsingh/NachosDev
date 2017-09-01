package nachos.threads;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nachos.ag.AutoGrader;
import nachos.machine.Config;
import nachos.machine.Lib;
import nachos.machine.Machine;
import nachos.machine.Machine.*;
import nachos.machine.TCB;
import nachos.security.*;
import nachos.ag.*;

public class KthreadTest {

	@Before
	public void setUp() throws Exception {
		System.out.println("Setting up for J unit test");
	    /**
	     * Nachos main entry point.
	     *
	     * @param	args	the command line arguments.
	     */
	    
		String args[]= new String[2];
		args[0]="-d";
		args[1]="t";
		
		Machine.main(args);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJoin() {
		//fail("Not yet implemented");
		System.out.println("testJoin");
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
