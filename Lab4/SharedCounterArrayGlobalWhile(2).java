import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedCounterArrayGlobalWhile {
  
    static int end = 10000;
    static int counter = 0;
    static int[] array = new int[end];
    static int numThreads = 4;
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        CounterThread threads[] = new CounterThread[numThreads];
	
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }
	
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {}
        } 
        check_array ();
    }
     
    static void check_array ()  {
        int i, errors = 0;

        System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
            lock.lock();
            try {
                if (array[i] != 1) {
                    errors++;
                    System.out.printf("%d: %d should be 1\n", i, array[i]);
                }
            } finally {
                lock.unlock();
            }       
        }
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {
  	
       public CounterThread() {
       }
  	
       public void run() {
       
            while (true) {
                lock.lock();
                try {
                    if (counter >= end)
                        break;
                    array[counter]++;
                    counter++;
                } finally {
                    lock.unlock();
                }   
            } 
        }            	
    }
}
