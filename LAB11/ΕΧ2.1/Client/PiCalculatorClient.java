import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PiCalculatorClient {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;
    private static final String EXIT_COMMAND = "EXIT";

    public static void main(String[] args) {
        String input = "";
        int n = 0;
        long numSteps = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            String rmiObjectName = "PiCalculator";
            PiCalculator ref = (PiCalculator) registry.lookup(rmiObjectName);

            System.out.print("number of steps:  ");
            input = reader.readLine();

            numSteps = Long.parseLong(in);
			result = ref.calculate(numSteps);
			System.out.println("The result is: " + result);
            

            
        } catch (RemoteException r) {
			System.out.println("Remote Exception");
			r.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}
    }
}
