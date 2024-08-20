public class ServerProtocol {
    private SharedData sharedData;

    public ServerProtocol(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    public String processRequest(String theInput) {
        System.out.println("Received message from client: " + theInput);
        String theOutput = theInput;
        if (!theOutput.equals("CLOSE")) {
            long numSteps = Long.parseLong(theInput);
            Double piValue;
            if (sharedData.hasPi(numSteps)) {
                piValue = sharedData.getPi(numSteps);
            } else {
                double sum = 0.0;
                double step = 1.0 / (double) numSteps;
                for (long i = 0; i < numSteps; ++i) {
                    double x = ((double) i + 0.5) * step;
                    sum += 4.0 / (1.0 + x * x);
                }
                double pi = sum * step;
                sharedData.putPi(numSteps, pi);
                piValue = pi;
            }
            theOutput = String.valueOf(piValue);
        }
        System.out.println("Send message to client: " + theOutput);
        return theOutput;
    }
}
