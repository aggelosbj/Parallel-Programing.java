public class NumIntSeq {

    public static void main(String[] args) {

        int numSteps = 10000000;
        double sum = 0.0;

        /* parse command line
        if (args.length != 1) {
            System.out.println("arguments:  number_of_steps");
            System.exit(1);
        }
        try {
            numSteps = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("argument " + args[0] + " must be integer");
            System.exit(1);
        }*/
        
        /* start timing */
        long startTime = System.currentTimeMillis();

        RecursiveTask task = new RecursiveTask(0, numSteps, (int) numSteps, new double[]{1.0 / (double) numSteps});
        Thread thread = new Thread(task);
        thread.start();

        /* wait for thread to finish */
        try {
            thread.join();
            sum = task.getPartialSum();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double step = task.getStep();
        /* do computation */
        sum += 4.0 / (1.0 + step * step / 4.0);

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("recursive program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class RecursiveTask implements Runnable {

    private int myFrom;
    private int myTo;
    private int myLimit;
    private double[] myA;
    private double partialSum;

    public RecursiveTask(int from, int to, int limit, double[] a) {
        this.myFrom = from;
        this.myTo = to;
        this.myLimit = limit;
        this.myA = a;
        this.partialSum = 0.0;
    }

    public double getStep() {
        return myA[0];
    }

    public double getPartialSum() {
        return partialSum;
    }

    public void run() {
        /* do recursion until limit is reached */
        int workload = myTo - myFrom;
        if (workload <= myLimit) {
            for (int i = myFrom; i < myTo; ++i) {
                double x = ((double) i + 0.5) * myA[0];
                partialSum += 4.0 / (1.0 + x * x);
            }
        } else {
            int mid = myFrom + workload / 2;
            RecursiveTask taskL = new RecursiveTask(myFrom, mid, myLimit, myA);
            Thread threadL = new Thread(taskL);
            threadL.start();
            RecursiveTask taskR = new RecursiveTask(mid, myTo, myLimit, myA);
            taskR.run();
            try {
                threadL.join();
                partialSum = taskL.getPartialSum() + taskR.getPartialSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}