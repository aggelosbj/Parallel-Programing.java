import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class NumIntSeq {

    public static void main(String[] args) {

        long numSteps = 10000;
        double sum = 0.0;
        Lock lock = new ReentrantLock();

       
        /* start timing */
        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double)numSteps;
        /* create and start threads */
        NumThread[] threads = new NumThread[Runtime.getRuntime().availableProcessors()];
        
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new NumThread(numSteps, i * (numSteps / threads.length), (i + 1) * (numSteps / threads.length), lock, sum);
            threads[i].start();
        }
        /* wait for threads to finish and collect results */
        for (NumThread thread : threads) {
            try {
                thread.join();
                sum += thread.getLocalSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("multi-threaded program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , sum * step);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(sum * step - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class NumThread extends Thread {

    private long start;
    private long end;
    private long numSteps;
    private Lock lock;
    private double localSum;
    private double sum;

    public NumThread(long numSteps, long start, long end, Lock lock, double sum) {
        this.start = start;
        this.end = end;
        this.numSteps = numSteps;
        this.lock = lock;
        this.localSum = 0.0;
        this.sum = sum;
    }

    public void run() {
        for (long i = start; i < end; i++) {
            double x = ((double) i + 0.5) * 1.0 / (double) numSteps;
            localSum += 4.0 / (1.0 + x * x);
        }
        lock.lock();
        try {
            sum += localSum;
        } finally {
            lock.unlock();
        }
    }

    public double getLocalSum() {
        return localSum;
    }
}
