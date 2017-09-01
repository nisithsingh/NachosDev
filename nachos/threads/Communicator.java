package nachos.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {
    /**
     * Allocate a new communicator.
     */
	Lock comm;
	int actvspeakers, actvlisteners, wtngspeakers, wtnglisteners;
	Condition2 isSpeakerCalled;
	Condition2 isListenerCalled;
	private LinkedList<Integer> spokenList;
	
	public LinkedList<Integer> getSpokenList(){
		return spokenList;
	}
	
    public Communicator() {
    	
    	comm = new Lock();
    	actvspeakers = 0;
    	actvlisteners = 0;
    	wtngspeakers = 0;
    	wtnglisteners = 0;
    	isSpeakerCalled = new Condition2(comm);
    	isListenerCalled = new Condition2(comm);
    	spokenList = new LinkedList<Integer>();

    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
    	System.out.println(KThread.currentThread()+ "currently speaking");
    	comm.acquire();
    	System.out.println("wtnglisteners: "+wtnglisteners+"and actvlisteners:"+actvlisteners);
    	while(wtnglisteners==0 && actvlisteners==0){    		
    		wtngspeakers++;
    		isListenerCalled.sleep();
    		wtngspeakers--;
    	}	
    	actvspeakers++;
    	spokenList.add(new Integer(word));
    	actvspeakers--;
    	System.out.println("wtnglisteners size: "+wtnglisteners);
    	if(wtnglisteners>0){    		
    		isSpeakerCalled.wake();
    	}	
    	comm.release();    	
    	System.out.println(KThread.currentThread()+" spoke :"+word);
    }
    

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
    	System.out.println(KThread.currentThread()+ "currently listening");
    	int listened;
    	comm.acquire();
    	while(spokenList.size()==0){    		
    		wtnglisteners++;
    		isSpeakerCalled.sleep();
    		wtnglisteners--;
    	}
    	actvlisteners++;
    	listened = spokenList.removeFirst().intValue();
    	actvlisteners--;
    	System.out.println("wtngspeakers size: "+wtngspeakers);
    	if(wtngspeakers>0){    		
    		isListenerCalled.wake();
    	}	
    	comm.release();
    	System.out.println(KThread.currentThread()+" heard :"+listened);
	return listened;
    }
    
    public static void selfTest() {
    	
    	KThread a[] = new KThread[5];
    	final Communicator one = new Communicator();
        	
    	for (int i=0;i<5;i++){
    		
    		if(i%2==0){
		    	a[i] = new KThread(new Runnable() {
				    public void run() { 
				    		Random rand = new Random();
				    		one.speak(rand.nextInt(50) + 1);
				    }
				});
    		}
    		else{
    			a[i] = new KThread(new Runnable() {
				    public void run() { 
				    		Random rand = new Random();
				    		one.listen();
				    }
				});
    		}
    		a[i].setName("Thread "+i);
        	a[i].fork();
    	}
    		
    		
    		System.out.println("Main Thread");
    		
//    		for (int i=0;i<5;i++){
    			a[1].join();
//    		}
    		
    		System.out.println("Printing spokenList size: "+  one.getSpokenList().size());
    		System.out.println("Main Thread Finishing");
        }
}
