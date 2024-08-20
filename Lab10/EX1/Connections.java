import java.util.Hashtable;
import java.util.Enumeration;

public class Connections {
    private final Hashtable<Integer, Connection> con;

    public Connections() {
        con = new Hashtable<>();
    }

    public synchronized void addConnection(int i, Connection c) {
        con.put(i, c);
    }

    public synchronized void removeConnection(int i) {
        if (con.containsKey(i)) {
            con.get(i).closeSocket();
            con.remove(i);
        }
    }

    public Connection getConnection(int i) {
        return con.get(i);
    }

    public int getSize() {
        return con.size();
    }

    public Enumeration<Integer> getKeys() {
        return con.keys();
    }

    public synchronized void broadcast(String message, int senderId) {
        for (int key : con.keySet()) {
            if (key != senderId) {
                con.get(key).send(message);
            }
        }
    }
}
