import java.util.ArrayList;

public class Buffer
{
	private ArrayList<Integer> contents; // Δυναμική δομη για απειρο 
	//private int[] contents;
	private boolean bufferEmpty = true;
	//private boolean bufferFull = false; o buffer δεν θα ειναι ποτε full
	//private int size; ειναι απειρο οποτε δεν χρειαζεται το μεγεθος
	private int front; //back δεν χρειαζομαστε αφου μονο θα αυξάνεται
	private int counter = 0;

	// Constructor
	public Buffer() {
		
		contents = new ArrayList<Integer>(0);
//	this.size = s;
//	contents = new int[size];
//	for (int i=0; i<size; i++)
//		contents[i] = 0;
//		this.front = 0;
//		this.back = -1;
	}

	// Put an item into buffer
	public synchronized void put(int data)
	{
//		while (bufferFull) {
//			try {
//				wait();
//			} catch (InterruptedException e) {}
//		}  δεν θα ειναι ποτε γεματος 
//		back = (back + 1) % size;
//		contents[back] = data;
		//κανουμε add αφου εχουμε την ArrayList
		contents.add(data);
		counter++;
		System.out.println("Item " + data + " added in loc " + contents.size() + ". Count = " + counter);
		bufferEmpty = false;
//		if (counter==size) δεν θα γεμισει ποτε
//		{
//			bufferFull = true;
//			System.out.println("The buffer is full");
//		} 
		//buffer was empty
		if (counter == 1) notifyAll();
	}

	// Get an item from bufffer
	public synchronized int get()
	{
		while (bufferEmpty) {
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		//int data = contents[front]; κανουμε παλι get 
		int data = contents.get(front);
		counter--;
		System.out.println("Item " + data + " removed from loc " + front + ". Count = " + counter);	
		front = (front + 1); 
				//% size;	δεν υπάρχει μεγεθος 
		//bufferFull = false; δεν θα ειναι ποτε γεματο 
		if (counter==0) 
		{
			bufferEmpty = true;
			System.out.println("The buffer is empty");
		}
		//buffer was full
		//if (counter == size-1) notifyAll();
		// απο την στιγμη που ο buffer δεν μπορει να γεμισει οι παραγωγοι 
		//δεν πρεπει ποτε να περιμενουν χωρις λογο ετσι η συνθηκη δεν ειναι 
		//απαραιτητη
		return data;
	}
}

	
			
	
