public class NumIntSeq {

    public static void main(String[] args) {

        long numSteps = 10000;
        double sum = 0.0;

        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double)numSteps;

        NumThread[] threads = new NumThread[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new NumThread(numSteps, i * (numSteps / threads.length), (i + 1) * (numSteps / threads.length));
            threads[i].start();
        }

        for (NumThread thread : threads) {
            try {
                thread.join();
                sum += thread.getSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.printf("multi-threaded program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , sum * step);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(sum * step - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class NumThread extends Thread {

    private long numSteps;
    private long start;
    private long end;
    private double sum;

    public NumThread(long numSteps, long start, long end) {
        this.numSteps = numSteps;
        this.start = start;
        this.end = end;
        this.sum = 0.0;
    }

    public void run() {
        for (long i = start; i < end; i++) {
            double x = ((double)i + 0.5) * (1.0 / (double)numSteps);
            sum += 4.0 / (1.0 + x * x);
        }
    }

    public double getSum() {
        return sum;
    }
}
