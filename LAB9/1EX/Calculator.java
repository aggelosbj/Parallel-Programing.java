public class Calculator {
    private char operation;
    private double firstNumber;
    private double secondNumber;
    private String result;

    public Calculator() {
        this.operation = ' ';
        this.firstNumber = 0;
        this.secondNumber = 0;
        this.result = "";
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public void setNumbers(double firstNumber, double secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public String calculate() {
        switch (operation) {
            case '+':
                result = String.valueOf(firstNumber + secondNumber);
                break;
            case '-':
                result = String.valueOf(firstNumber - secondNumber);
                break;
            case '*':
                result = String.valueOf(firstNumber * secondNumber);
                break;
            case '/':
                if (secondNumber == 0) {
                    result = "Error: Division by zero";
                } else {
                    result = String.valueOf(firstNumber / secondNumber);
                }
                break;
            default:
                result = "Error: Invalid operation";
        }
        return result;
    }
}
