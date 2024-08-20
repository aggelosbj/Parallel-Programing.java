import java.util.concurrent.Semaphore;
public class Buffer
{
	 private int content;  // Για ένα buffer μιας θέσης αρκεί απλή μεταβλητή int
	// private int[] contents; // Δεν χρειάζεται πλέον πίνακας για ένα buffer μιας θέσης 
	// private int size;  // Η μεταβλητή size δεν χρειάζεται πλέον
	// private int front, back;  // Δεν χρειάζεται πλέον η μεταβλητή back και front 
	 private int counter = 0; 
	//private Semaphore mutex = new Semaphore(1); Δεν χρειαζόμαστε 3 semaphors ακρούνε 2 ένα για empty and full
	private Semaphore bufferFull = new Semaphore(0); // είναι γεμάτο 
	private Semaphore bufferEmpty =new Semaphore(1);  //έχει μια θέση κενή 

	// Constructor
	public Buffer( ) {
		// this.size = s; // Δεν χρειάζεται πλέον η μεταβλητή size
        content = 0;  // Αρχικοποίηση του περιεχομένου του buffer σε μηδέν
        // contents = new int[size]; // Δεν χρειάζεται πλέον πίνακας contents
        // for (int i = 0; i < size; i++) // Δεν χρειάζεται πλέον αρχικοποίηση του πίνακα contents
        //    contents[i] = 0;
        //this.front = 0;
        // this.back = size - 1; // Δεν χρειάζεται πλέον η μεταβλητή back
		// this.bufferEmpty = new Semaphore(size); περιτό αφου έχουμε μόνο μια θέση 
	}

	// Put an item into buffer
	public void put(int data) {
		try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) { }
		//try {
		//	mutex.acquire();
		//} catch (InterruptedException e) { }
		// back = (back + 1) % size; // Δεν χρειάζεται back για ένα buffer μιας θέσης αφου δεν υπάρχει back 
		// contents[back] = data; // Δεν χρειάζεται πλέον ο πίνακας contents λόγω της μία θέσης 
		content = data;  // Αποθηκεύει τα δεδομένα στο buffer
		counter++; 
		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Count = " + counter);
		//if (counter == size)
		System.out.println("The buffer is full");  // Το buffer είναι πάντα γεμάτο όταν έχει μέγεθος ένα
		
		//mutex.release();
		bufferFull.release(); 
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) { }
		//try {
		//	mutex.acquire();
		//} catch (InterruptedException e) { }
		// data = contents[front]; // Δεν χρειάζεται πλέον ο πίνακας contents
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data +  " Count = " + (counter-1));
		//δεν χρειάζεται να καθορσίσουμε τις θέσεις του buffer αφου εχει πάντα μέγεθος 1 
		 data = content;  // Παίρνει τα δεδομένα από το buffer
		 
	     System.out.println("The buffer is empty");  // Το buffer είναι πάντα άδειο όταν έχει μέγεθος ένα
		//front = (front + 1) % size; Δεν έχουμε front 
		counter--;	
		//if (counter == 0) System.out.println("The buffer is empty");	
		//mutex.release();		
		bufferEmpty.release();
		return data;
	}
}

	/*
	 * Buffer buff = new Buffer();
	 * βγάζουμε το size αφού δεν είναι πλέον απαραίτητο 
	 * 
	 */
			
	
