

public class SharedCounterArrayGlobalWhile {
  
    // static int end = 10000;
    // static int counter = 0;
    // static int[] array = new int[end];
    // static int numThreads = 4;

    public static void main(String[] args) {
        int end = 10000;
        int[] array = new int[end];
        int numThreads = 4;

        SharedCounter sharedCounter = new SharedCounter(array, 0, end);
        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(sharedCounter);
            threads[i].start();
        }

        for (CounterThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        checkArray(sharedCounter.getArray());
    }

    static void checkArray(int[] array) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < array.length; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }


    static class CounterThread extends Thread {
        private SharedCounter sharedCounter;

        public CounterThread(SharedCounter sharedCounter) {
            this.sharedCounter = sharedCounter;
        }

       
        public void run() {
            while (true) {
                sharedCounter.increment();
                if (sharedCounter.counter >= sharedCounter.end)
                    break;
            }
        }
    }

	static class SharedCounter {
        private int[] array;
        private int counter;
        private int end;

        public SharedCounter(int[] array, int counter, int end) {
            this.array = array;
            this.counter = counter;
            this.end = end;
        }

        public synchronized void increment() {
            if (counter < end) {
                array[counter]++;
                counter++;
            }
        }

        public int[] getArray() {
            return array;
        }
    }
}
  
