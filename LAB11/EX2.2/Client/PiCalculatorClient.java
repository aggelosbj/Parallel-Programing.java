import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class PiCalculatorClient {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;
   

    public static void main(String[] args) {
        String input = "";
        long numSteps = 0;
        double result = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            String rmiObjectName = "MyCalculator";
            PiCalculator ref = (PiCalculator) registry.lookup(rmiObjectName);

            System.out.println("Enter number of steps to calculate Pi or type CLOSE to exit:");
            
            input = reader.readLine();
            while (!input.equalsIgnoreCase("CLOSE")) {
                if (input.matches("\\d+")) {
                    numSteps = Long.parseLong(input);
                    result = ref.calculate(numSteps);
                    System.out.println("The result is: " + result);
                } else {
                    System.out.println("*Invalid input. Give number of steps, or type CLOSE to exit.*");
                }
                System.out.print("Calculate: ");
                input = reader.readLine();
            }

        } catch (RemoteException r) {
            System.out.println("Remote Exception");
            r.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }
    }
}
