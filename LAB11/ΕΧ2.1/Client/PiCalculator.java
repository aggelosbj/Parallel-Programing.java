import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PiCalculator extends Remote {
    
    // Ypografh ths apomakrysmenhs methodoy.
    public double calculatePi(long n) throws RemoteException;
}
