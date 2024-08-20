import java.net.*;
import java.io.*;

public class WorkerProtocol {

    public WorkerProtocol() {
    }

    public String compute(String theInput) {
        String[] splited = theInput.split("\\s+");
        int numSteps = Integer.parseInt(splited[0]);
        int id = Integer.parseInt(splited[1]);

        int numWorkers = 4; 
        double step = 1.0 / (double) numSteps;
        double sum = 0.0;

        int start = id * (numSteps / numWorkers);
        int end = (id + 1) * (numSteps / numWorkers);

        System.out.println("Worker " + id + " calculates pi from " + start + " to " + end);

        for (int i = start; i < end; ++i) {
            double x = ((double) i + 0.5) * step;
            sum += 4.0 / (1.0 + x * x);
        }

        double result = sum * step;
        System.out.println("Worker " + id + " result " + result);
        return String.valueOf(result);
    }
}


class WorkerThread extends Thread {
    private int start, stop;
    private double mySum, step;

    public WorkerThread(int st, int sp, double s){
        start = st;
        stop = sp;
        step = s;
        mySum = 0.0;
    }

    public void run(){
        for (int i = start; i < stop; ++i) {
            double x = ((double)i + 0.5) * step;
            mySum += 4.0 / (1.0 + x * x);
        }
    }

    public double getSum(){
        return mySum;
    }
}
