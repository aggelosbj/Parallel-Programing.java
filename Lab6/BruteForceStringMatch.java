import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BruteForceStringMatch {

    public static void main(String args[]) throws IOException {

        if (args.length != 2) {
            System.out.println("BruteForceStringMatch  <file name> <pattern>");
            System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));
        char[] text = fileString.toCharArray();

        String patternString = args[1];
        char[] pattern = patternString.toCharArray();

        int matchLength = text.length - pattern.length;
        char[] match = new char[matchLength + 1];
        for (int i = 0; i <= matchLength; i++) {
            match[i] = '0';
        }

        
        int matchCount = 0;

        // Start timing
        long startTime = System.currentTimeMillis();

        // Create and start threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        MatchThread[] threads = new MatchThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MatchThread(text, pattern, match, i * (matchLength / numThreads), (i + 1) * (matchLength / numThreads), lock);
            threads[i].start();
        }

        // Wait for threads to finish
        for (MatchThread thread : threads) {
            try {
                thread.join();
                matchCount += thread.getMatchCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // End timing and print result
        long endTime = System.currentTimeMillis();

        for (int i = 0; i < matchLength; i++) {
            if (match[i] == '1') System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Total matches " + matchCount);
        System.out.println("Time to compute: " + (endTime - startTime) + " milliseconds");
    }
}

class MatchThread extends Thread {

    private final char[] text;
    private final char[] pattern;
    private final char[] match;
    private final int start;
    private final int end;
    private int matchCount;

    public MatchThread(char[] text, char[] pattern, char[] match, int start, int end, Lock lock) {
        this.text = text;
        this.pattern = pattern;
        this.match = match;
        this.start = start;
        this.end = end;
        
        this.matchCount = 0;
    }

    public void run() {
        for (int j = start; j < end; j++) {
            int i;
            for (i = 0; i < pattern.length && pattern[i] == text[i + j]; i++);
            if (i >= pattern.length) {   
                    match[j] = '1';
                    matchCount++;
                
            }
        }
    }

    public int getMatchCount() {
        return matchCount;
    }
}

