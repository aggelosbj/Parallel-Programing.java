import java.util.concurrent.Semaphore;

class linearBarrier {
    private int arrived = 0;
    private int totalThreads;
    private Semaphore mutex = new Semaphore(1);
    private Semaphore waiting = new Semaphore(0);
    private Semaphore leaving = new Semaphore(0);
    
    public linearBarrier(int total) {
        this.totalThreads = total;
    }        

    public void barrier() {
        try {
            mutex.acquire();
            arrived++;
            
            if (arrived == totalThreads) {
                waiting.release(totalThreads);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
        
        try {
            waiting.acquire();
            waiting.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        try {
            mutex.acquire();
            arrived--;
            
            if (arrived == 0) {
                leaving.release(totalThreads);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
        
        try {
            leaving.acquire();
            leaving.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
