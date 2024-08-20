import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SieveOfEratosthenesThird {
    static int size;
    static boolean[] prime;
    static int tasksAssigned = -1;
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SieveOfEratosthenesDynamic <size>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }
        if (size <= 0) {
            System.out.println("size should be positive integer");
            System.exit(1);
        }

        prime = new boolean[size + 1];

        for (int i = 2; i <= size; i++)
            prime[i] = true;

        // get current time
        long start = System.currentTimeMillis();

        // Create and start worker threads
        int nThreads = Runtime.getRuntime().availableProcessors(); 
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(new Worker());
        }
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }

        // Join worker threads
        for (int i = 0; i < threads.length; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println("This should not happen");
            }
        }

        // Calculate elapsed time
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        int count = 0;
        for (int i = 2; i <= size; i++)
            if (prime[i] == true) {
                //System.out.println(i);
                count++;
            }

        System.out.println("number of primes " + count);
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

    
    private static class Worker implements Runnable {
        public void run() {
            int limit = (int) Math.sqrt(size) + 1;
            int element;
            while ((element = getTask(limit)) >= 0) {
                if (prime[element] == true) {
                    for (int i = element * element; i <= size; i += element)
                        prime[i] = false;
                }
            }
        }
    }

    
    private static int getTask(int limit) {
        lock.lock();
        try {
            if (++tasksAssigned < limit)
                return tasksAssigned;
            else
                return -1;
        } finally {
            lock.unlock();
        }
    }
}
