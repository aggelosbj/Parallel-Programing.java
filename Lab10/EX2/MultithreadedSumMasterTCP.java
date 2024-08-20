import java.net.*;
import java.io.*;

public class MultithreadedSumMasterTCP {
    private static final int PORT = 1234;
    private static final int numWorkers = 4;
    private static final int numSteps = 100000;

    public static void main(String args[]) throws IOException {
        ServerSocket connectionSocket = new ServerSocket(PORT);
        Pi pi = new Pi();
        MasterThread[] mthread = new MasterThread[numWorkers];

        for (int i = 0; i < numWorkers; i++) {
            Socket dataSocket = connectionSocket.accept();
            mthread[i] = new MasterThread(dataSocket, i, pi, numSteps);
            mthread[i].start();
        }

        System.out.println("All Started");

        for (int i = 0; i < numWorkers; i++) {
            try {
                mthread[i].join();
            } catch (InterruptedException e) {}
        }

        pi.printResult();
    }
}
