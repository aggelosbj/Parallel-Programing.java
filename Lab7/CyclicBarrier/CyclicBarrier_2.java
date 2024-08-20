public class linearBarrier {
    private int arrived = 0;
    private int totalThreads;
    private boolean waiting = false;
    private boolean leaving = true;

    public linearBarrier(int total) {
        this.totalThreads = total;
    }

    public synchronized void barrier() throws InterruptedException {
        wait_barrier();
        leave_barrier();
    }

    public synchronized void wait_barrier() throws InterruptedException {
        arrived++;
        while (waiting) {
            wait();
        }
        if (arrived == totalThreads) {
            waiting = true;
            leaving = false;
            notifyAll();
        } else {
            while (!waiting) {
                wait();
            }
        }
    }

    public synchronized void leave_barrier() throws InterruptedException {
        arrived--;
        while (leaving) {
            wait();
        }
        if (arrived == 0) {
            waiting = false;
            leaving = true;
            notifyAll();
        } else {
            while (waiting) {
                wait();
            }
        }
    }
}
