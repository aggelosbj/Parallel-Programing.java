import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class Buffer
{
	private int contents; //για μόνο μια θέση
	
	//private int[] contents; έχουμε μόνο μια θέση δεν θέλουμε πίνακα
	//private int size; έχουμε μόνο μια θέση
//	private int front, back; δεν χρειάζονται οι front and back για μια μόνο θεση
	private int counter = 0;
	private Lock lock = new ReentrantLock();
	//private Condition bufferFull = lock.newCondition(); 
	//private Condition bufferEmpty = lock.newCondition();
	private Condition condition = lock.newCondition(); // αφου εχουμε  1 θεση 
	
	// Constructor
	public Buffer() {
		contents = 0;
//	this.size = s;
//	contents = new int[size];
//	for (int i=0; i<size; i++)
//		contents[i] = 0;
//		this.front = 0;
//		this.back = size - 1;
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
			try {
				while (counter == 1) {
				System.out.println("The buffer is full");
				try {
					condition.await();
				} catch (InterruptedException e) { }
			}
			//back = (back + 1) % size; δεν έχουμε back
			contents = data;
			counter++;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data +   " Count = " + counter);
			//buffer was empty
			//if (counter == 1)
			//αφου εχουμε μονο μια θεση μετα απο κάθε στοιχείο ενημερώνουμε 
				condition.signalAll();
				//
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
					condition.await();
				} catch (InterruptedException e) { }
			}
			data = contents;
			counter--;
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data  + " Count = " + (counter));
			//front = (front + 1) % size; δεν εχουμε front 
			
			//buffer was full
			//if (counter == size-1)
			//το ιδιο με επάνω 
				condition.signalAll();
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
