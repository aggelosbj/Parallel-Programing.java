import java.util.ArrayList;
import java.util.concurrent.Semaphore;
public class Buffer
{
	private ArrayList<Integer> contents;
	//private int[] contents;  Θα το κάνουμε με ArrayList 
	//private int size; Δεν υπάρχει κάποιο συγκεκριμένο μέγεθος αφου κάνουμε για size άπειρο 
	//private int front, back;  δεν θα τα χρειαστούμε αφου arrayList αντικαθιστά την λειτουργία των front και back 
	private int counter = 0;
	private Semaphore mutex = new Semaphore(1);
	//private Semaphore bufferFull = new Semaphore(0); Ο Buffer δεν θα γεμίσει ποτέ αφου το κάνουμε για άπειρο 
	private Semaphore bufferEmpty = new Semaphore(0); // το κανουμε 0 να ξεκιναει άδειο   

	// Constructor
	public Buffer() {
		contents = new ArrayList<>();
		
//	this.size = s;
//	contents = new int[size];
//	for (int i=0; i<size; i++)
//		contents[i] = 0;
//		this.front = 0;
//		this.back = size-1;	
//		this.bufferEmpty = new Semaphore(size);
	}

	// Put an item into buffer
	public void put(int data) {
		//try {
			//bufferEmpty.acquire(); δεν υπάρχει νοήμα σε μια λίστα που μπορεί να έχει απειρό μέγεθος 
		//} catch (InterruptedException e) { }
		try {
			mutex.acquire();
		} catch (InterruptedException e) { }
		//back = (back + 1) % size;
		//contents[back] = data;
		contents.add(data); // Προσθέτουμε το στοιχείο data στο τέλος της λίστας contents
		counter++; // Αυξάνουμε τον μετρητή κατά ένα, δείχνοντας το πλήθος των στοιχείων στη λίστα
		// Εκτύπωση πληροφοριών για το στοιχείο που προστέθηκε
		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + (counter - 1) + " Count = " + counter);
		//if (counter == size) System.out.println("The buffer is full");
		bufferEmpty.release(); // σημανσή οτι ο buffer δεν είναι άδειος 
		mutex.release(); 
		//bufferFull.release(); // Δεν απαιτείται καθώς το μέγεθος της λίστας είναι άπειρο
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferEmpty.acquire(); // Περιμενει μεχρι buffer να μην ειναι αδειος 
			//bufferFull.acquire(); Δεν χρειαζεται να αποκτησουμε αδεια απο bufferFull αφου δεν γεμιζει ποτε 
		} catch (InterruptedException e) { }
		try {
			mutex.acquire(); 
		} catch (InterruptedException e) { }
		//data = contents[front]; // Δεν χρειάζεται να υπολογίσουμε το front γιατί χρησιμοποιούμε τη μέθοδο remove(0) 
		// Θα μπορούσαμε να αφλησουμε την front θα είχαμε τα ίδια αποτελέσματα
		data = contents.remove(0); // Αφαιρεί το πρώτο στοιχείο απο το buffer
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + 0 + " Count = " + (counter-1));
        //front = (front + 1) % size; το καναμε με remove(0)
		counter--;	
		if (counter == 0) System.out.println("The buffer is empty");	
		mutex.release();		
		//bufferEmpty.release();
		return data;
	}
}

	
			
	