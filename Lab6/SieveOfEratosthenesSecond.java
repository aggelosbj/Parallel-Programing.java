import java.util.ArrayList;
import java.util.List;

class SieveOfEratosthenesSecond {
    public static void main(String[] args) {
        int size = 0;
        if (args.length != 1) {
            System.out.println("Usage: java SieveOfEratosthenes <size>");
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

        boolean[] prime = new boolean[size + 1];

        for (int i = 2; i <= size; i++)
            prime[i] = true;

        // get current time 
        long start = System.currentTimeMillis();

        int limit = (int) Math.sqrt(size) + 1;
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available processors
        List<SieveThread> threads = new ArrayList<>();

        for (int t = 0; t < numThreads; t++) {
            SieveThread thread = new SieveThread(prime, size, t, numThreads);
            threads.add(thread);
            thread.start();
        }

        for (SieveThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        int count = 0;
        for (int i = 2; i <= size; i++)
            if (prime[i] == true) {
                count++;
            }

        System.out.println("number of primes " + count);
        System.out.println("time in ms = " + elapsedTimeMillis);
    }

    private static class SieveThread extends Thread {
        private boolean[] prime;
        private int size;
        private int threadId;
        private int numThreads;

        public SieveThread(boolean[] prime, int size, int threadId, int numThreads) {
            this.prime = prime;
            this.size = size;
            this.threadId = threadId;
            this.numThreads = numThreads;
        }

        public void run() {
            for (int p = 2 + threadId; p * p <= size; p += numThreads) {
                if (prime[p]) {
                    for (int i = p * p; i <= size; i += p) {
                        if (i < prime.length) {
                            prime[i] = false;
                        }
                    }
                }
            }
            
        }
    }
}
