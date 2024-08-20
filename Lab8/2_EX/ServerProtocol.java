public class ServerProtocol {
    private static final String EXIT_COMMAND = "CLOSE";
    private Calculator calculator;

    public ServerProtocol() {
        this.calculator = new Calculator();
    }

    public String processRequest(String request) {
        if (request.equals(EXIT_COMMAND)) {
            return EXIT_COMMAND;
        }

        String[] parts = request.split(" ");
        if (parts.length != 3) {
            return "E Invalid input format";
        }

        double a, b;
        try {
            a = Double.parseDouble(parts[0]);
            b = Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            return "E Non-integer values";
        }

        String op = parts[1];
        calculator.setOperation(op.charAt(0));
        calculator.setNumbers(a, b);

        return calculator.calculate();
    }
}
