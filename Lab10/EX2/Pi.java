public class Pi {
    private double sum;

    public Pi() {
        sum = 0.0;
    }

    public synchronized void addTo(double toAdd) {
        sum += toAdd;
    }

    public synchronized void printResult() {
        System.out.println("Estimated value of Pi = " + sum);
    }
}
