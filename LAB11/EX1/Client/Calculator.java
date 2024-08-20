import java.rmi.*;

public interface Calculator extends Remote {
	public String calculate(double a, double b, String operation) throws RemoteException;
}
