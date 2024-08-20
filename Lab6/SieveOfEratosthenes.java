import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SieveOfEratosthenes {

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

        SharedData sharedData = new SharedData(size);

        long startTime = System.currentTimeMillis();

        int numThreads = Runtime.getRuntime().availableProcessors();
        long limit = (int) Math.sqrt(size) + 1;

        SieveOfEratosthenesThread[] threads = new SieveOfEratosthenesThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SieveOfEratosthenesThread(i, numThreads, size, limit, sharedData);
            threads[i].start();
        }

        for (SieveOfEratosthenesThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        int count = sharedData.getPrimeCount();

        System.out.println("Number of primes: " + count);
        System.out.println("Time in ms: " + (endTime - startTime));
    }

    private static class SieveOfEratosthenesThread extends Thread {
        private final int myId;
        private final int myStart;
        private final int myStop;
        private final SharedData data;
        private final long limit;
        private final int size;

        public SieveOfEratosthenesThread(int id, int numThreads, int size, long limit, SharedData data) {
            this.size = size;
            this.data = data;
            myId = id;
            myStart = (int) (myId * (limit / numThreads));
            if (myId == (numThreads - 1)) {
                myStop = (int) limit;
            } else {
                myStop = (int) (myStart + (limit / numThreads));
            }
            this.limit = limit;
        }
        
        

        @Override
        public void run() {
            for (int p = myStart; p <= myStop; p++) {
                if (data.isPrime(p)) {
                    for (long i = (long) p * p; i <= data.getSize(); i += p) {
                        if (i >= myStart && i % p == 0) {
                            data.markNonPrime((int) i);
                        }
                    }
                }
            }
        }
    }

    private static class SharedData {
        private final boolean[] prime;
        private final int size;

        public SharedData(int size) {
            this.size = size;
            this.prime = new boolean[size + 1];
            
            for (int i = 2; i <= size; i++) {
                prime[i] = true;
            }
        }

        public int getSize() {
            return size;
        }

        public boolean isPrime(int num) {
            return prime[num];
        }

        public void markNonPrime(int num) {
                prime[num] = false;
           
        }

        public int getPrimeCount() {
            int count = 0;
            for (int i = 2; i <= size; i++) {
                if (prime[i]) {
                    count++;
                }
            }
            return count;
        }
    }
}