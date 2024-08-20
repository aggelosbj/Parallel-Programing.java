import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CalculatorClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; 
	
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		double a = 0;
		double b = 0;
		String result = "";
		
		System.out.println("This app calculates (+, -, *, /) the result between two numbers.");
		System.out.println("*Message Format: a <space> op <space> b *");
		
		try {
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			String rmiObjectName = "MyCalculator";
			Calculator ref = (Calculator) registry.lookup(rmiObjectName);

			System.out.print("Type your calculation  ");
			input = reader.readLine();
			
			while (!input.equalsIgnoreCase("CLOSE")) {
				String[] parts = input.split(" ");
				if (parts.length == 3 && isNumeric(parts[0]) && isNumeric(parts[2])) {
					a = Double.parseDouble(parts[0]);
					String op = parts[1];
					b = Double.parseDouble(parts[2]);

					result = ref.calculate(a, b, op);
					System.out.println("Result: " + result);
				} else {
					System.out.println("Wrong message format(a <space> op <space> b).");
				}

				System.out.print("Enter your calculation or CLOSE to exit: ");
				input = reader.readLine();
			}
			
			System.out.println("Client closed.");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  
	}
}
