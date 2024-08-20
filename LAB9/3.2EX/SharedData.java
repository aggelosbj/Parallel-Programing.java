import java.util.concurrent.ConcurrentHashMap;

public class SharedData {
    private ConcurrentHashMap<Long, Double> piCache = new ConcurrentHashMap<>();

    public synchronized double getPi(long n) {
        return piCache.get(n);
    }

    public synchronized void putPi(long n, double pi) {
        piCache.put(n, pi);
    }

    public synchronized boolean hasPi(long n){
        if(piCache.get(n) != null)
        return true;
        return false;
    }
}
