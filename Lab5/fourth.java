import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class NumIntSeq {

    public static void main(String[] args) {

        long numSteps = 10000;
        double sum = 0.0;
        Lock lock = new ReentrantLock();

        /* start timing */
        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double) numSteps;
        /* create and start threads */
        NumThread[] threads = new NumThread[Runtime.getRuntime().availableProcessors()];

        long stepsPerThread = numSteps / threads.length;

        for (int i = 0; i < threads.length; i++) {
            long threadStart = i * stepsPerThread;
            long threadEnd = (i + 1) * stepsPerThread;
            threads[i] = new NumThread(numSteps, threadStart, threadEnd, lock);
            threads[i].start();
        }
        /* wait for threads to finish and collect results */
        for (NumThread thread : threads) {
            try {
                thread.join();
                sum += thread.getSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("multi-threaded program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", sum * step);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(sum * step - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}



class NumThread extends Thread {

    private long start;
    private long end;
    private long numSteps;
    private Lock lock;
    private double sum;

    public NumThread(long numSteps, long start, long end, Lock lock) {
        this.start = start;
        this.end = end;
        this.numSteps = numSteps;
        this.lock = lock;
        this.sum = 0.0;
    }

    public void run() {
        for (long i = start; i < end; i++) {
            double x = ((double) i + 0.5) * 1.0 / (double) numSteps;
            lock.lock();
            try {
                sum += 4.0 / (1.0 + x * x);
            } finally {
                lock.unlock();
            }
        }
    }

    public double getSum() {
        return sum;
    }
}
