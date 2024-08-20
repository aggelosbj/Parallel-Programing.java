import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class PiCalculatorImpl extends UnicastRemoteObject implements PiCalculator {
   
    private static final long serialVersionUID = 1;

    protected PiCalculatorImpl() throws RemoteException {
        super();
    }

    public double calculate(long numSteps) throws RemoteException {
        double sum = 0.0;
        double step = 1.0 / (double)numSteps;
        for (long i=0; i < numSteps; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
        double pi = sum * step;
        return pi;
    }
}
