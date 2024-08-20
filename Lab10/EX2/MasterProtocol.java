import java.net.*;
import java.io.*;

public class MasterProtocol {

    private Pi pi;
    private int myid;
    private int numSteps;

    public MasterProtocol(Pi p, int id, int n){
        pi = p;
        myid = id;
        numSteps = n; 
    }

    public String prepareRequest() {
        String theOutput = String.valueOf(numSteps) + " " + String.valueOf(myid);  // Διόρθωση: χρήση του myid.
        return theOutput;
    }
    
    public void processReply(String theInput) {
        double repl = Double.parseDouble(theInput);
        pi.addTo(repl);  
    }   
}
