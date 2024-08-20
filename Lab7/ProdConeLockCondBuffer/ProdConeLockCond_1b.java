import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Buffer
{
	private ArrayList<Integer> contents; // Κάνουμε μια array για δυναμική δομή ώστε να έχουμε άπειρο buffer
	//private int[] contents;
	//private int size; 
	private int front;// εβγαλά το back αφου απλά θα αυξάνει 
	private int counter = 0;
	private Lock lock = new ReentrantLock();
	//private Condition bufferFull = lock.newCondition(); δεν υα γεμισει ποτε
	private Condition bufferEmpty = lock.newCondition();

	// Constructor
	public Buffer() {
		contents = new ArrayList<Integer>();
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
			try {
//				while (counter == size) {
//				System.out.println("The buffer is full");
//				try {
//					bufferFull.await();
//				} catch (InterruptedException e) { }
//			}
//			back = (back + 1) % size;
//			contents[back] = data;
				//το παραπάνω το εβγαλα αφου buffer δεν θα ειναι full 
				
			contents.add(data); // προσθέτω τα δεδομένα στον buffer 
			counter++;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + contents.size() + " Count = " + counter);
			//buffer was empty
			if (counter == 1) bufferEmpty.signalAll();
		} finally {
			lock.unlock() ;
		}
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;

		lock.lock();
		try {
			while (counter == 0) {
				System.out.println("The buffer is empty");
				try {
					bufferEmpty.await();
				} catch (InterruptedException e) { }
			}
			//data = contents[front];
			
			//αφου εχουμε array κανουμε get και αποκταμε το δεδομένο 
			data = contents.get(front);
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + front + " Count = " + (counter-1));
			front = (front + 1) ;
			counter--;
			//buffer was full
			//if (counter == size-1) bufferFull.signalAll();
			//αφου δεν γεμίζει ποτε δεν χρειάζεται να ενημερώσει 
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
