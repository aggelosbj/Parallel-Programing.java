class DoubleCounter {
    private int n1;
    private int n2;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void incrementN1() {
        synchronized (lock1) {
            n1++;
        }
    }

    public void incrementN2() {
        synchronized (lock2) {
            n2++;
        }
    }

    public int getN1() {
        synchronized (lock1){
        return n1;
        }
    }

    public int getN2() {
        synchronized (lock2){
        return n2;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DoubleCounter counter = new DoubleCounter();
        Thread[] threads = new Thread[100];

        for (int i = 0; i < 100; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                if (threadNumber < 50) {
                    for (int j = 0; j < 10000; j++) {
                        counter.incrementN1();
                    }
                } else {
                    for (int j = 0; j < 10000; j++) {
                        counter.incrementN2();
                    }
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("n1: " + counter.getN1());
        System.out.println("n2: " + counter.getN2());
    }
}