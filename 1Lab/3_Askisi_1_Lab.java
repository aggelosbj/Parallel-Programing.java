class MultiplicationThread extends Thread {
    private int number;

    public MultiplicationThread(int number) {
        this.number = number;
    }

    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println(number + " * " + i + " = " + (number * i));
            try {
                Thread.sleep(100); // Προσωρινή καθυστέρηση για να επιτραπεί η ταυτόχρονη εκτύπωση
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MultiplicationThread[] threads = new MultiplicationThread[10];

        // Δημιουργία και εκκίνηση των νημάτων
        for (int i = 0; i < 10; i++) {
            threads[i] = new MultiplicationThread(i + 1);
            threads[i].start();
        }
    }
}


//Όταν απομωνόνουμε τις εκτυπώσεις ενός νήματος, παρατηρούμε ότι οι εκτυπώσεις 
//αυτού του νήματος εμφανίζονται μαζί αφου εκτελεί τις εντολές του σειριακά και χωρίς διακοπές

//Όταν όλα τα νήματα εκτυπώνουν ταυτόχρονα, παρατηρούμε μια ταυτόχρονη έξοδο των 
//μηνυμάτων στην εκτύπωση αυτό συμφαίνει αφού τα νήματα εκτελούνται παράλληλα  χώρις 
// να περιμένουν το ένα το άλλο.
