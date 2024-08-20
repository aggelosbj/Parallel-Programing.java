import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

	protected CalculatorImpl() throws RemoteException {
		super();
	}

	
	public String calculate(double a, double b, String operation) throws RemoteException {
		switch (operation) {
			case "+":
				return String.valueOf(a + b);
			case "-":
				return String.valueOf(a - b);
			case "*":
				return String.valueOf(a * b);
			case "/":
				if (b == 0) {
					return "False: Division by zero";
				} else {
					return String.valueOf(a / b);
				}
			default:
				return "False: Invalid operation";
		}
	}
}
