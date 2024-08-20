import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class linearBarrier {
    private int arrived = 0;
    private int totalThreads;
    private boolean waiting = true;
    private Lock lock = new ReentrantLock();
    private Condition barrierReached = lock.newCondition();
    private Condition barrierPassed = lock.newCondition();

    public linearBarrier(int total) {
        this.totalThreads = total;
    }

    public void barrier() {
        lock.lock();
        try {
            arrived++;
            if (arrived == totalThreads) {
                waiting = false;
                barrierReached.signalAll();
            }
            while (waiting) {
                try {
                    barrierReached.await();
                } catch (InterruptedException e) {
                }
            }
            arrived--;
            if (arrived == 0) {
                waiting = true;
                barrierPassed.signalAll();
            }
            while (!waiting) {
                try {
                    barrierPassed.await();
                } catch (InterruptedException e) {
                }
            }
        } finally {
            lock.unlock();
        }
    }
}