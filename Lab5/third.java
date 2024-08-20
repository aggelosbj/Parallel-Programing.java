public class NumIntSeq {

    public static void main(String[] args) {

        long numSteps = 10000;
        double sum = 0.0;

        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double) numSteps;

        NumThread[] threads = new NumThread[Runtime.getRuntime().availableProcessors()];

        SumHolder sumHolder = new SumHolder();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new NumThread(numSteps, i * (numSteps / threads.length), (i + 1) * (numSteps / threads.length), sumHolder);
            threads[i].start();
        }

        for (NumThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sum = sumHolder.getSum();

        long endTime = System.currentTimeMillis();
        System.out.printf("multi-threaded program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", sum * step);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(sum * step - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class SumHolder {
    private double sum;

    public SumHolder() {
        this.sum = 0.0;
    }

    public synchronized void addToSum(double value) {
        sum += value;
    }

    public double getSum() {
        return sum;
    }
}

class NumThread extends Thread {

    private long start;
    private long end;
    private long numSteps;
    private SumHolder sumHolder;

    public NumThread(long numSteps, long start, long end, SumHolder sumHolder) {
        this.numSteps = numSteps;
        this.start = start;
        this.end = end;
        this.sumHolder = sumHolder;
    }

    public void run() {
        double localSum = 0.0;
        for (long i = start; i < end; i++) {
            double x = ((double) i + 0.5) * 1.0 / (double) numSteps;
            localSum += 4.0 / (1.0 + x * x);
            
        }
        sumHolder.addToSum(localSum);
    }
}
