public class Buffer
{
	private int contents; //αφου έχουμε μια θέση 
	
	//private int[] contents;
	//private boolean bufferEmpty = true;
	////private boolean bufferFull = false;
	//δεν χρειαζόμαστε το bufferEmpty kai bufferFull διότι έχουμε το 
	//synxhronized που αφήνει μόνο ενα νήμα την φορα
	//private int size; εχουμε μονο μια θεση
	//private int front, back;
	private int counter = 0;

	// Constructor
	public Buffer() {
		
		contents = 0;
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
		while (counter == 1 ) {   //bufferFull βαζουμε 1 αφου ειναι το μεγεθος
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		//back = (back + 1) % size; δεν εχουμε μεγεθος
		//contents[back] = data;
		contents = data;
		counter++;
		System.out.println("Item " + data +  ". Count = " + counter);
		//bufferEmpty = false;
		if (counter==1)
		{
			//bufferFull = true;
			System.out.println("The buffer is full");
		}
		//buffer was empty
		//if (counter == 1) 
			notifyAll(); // κάνουμε notify αφου εχουμε μονο μια θεση 
	}

	// Get an item from bufffer
	public synchronized int get()
	{
		while (counter ==  0) { //while (bufferEmpty)
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		//int data = contents[front];
		int data = contents;
		counter--;
		System.out.println("Item " + data + " removed from. Count" + counter);	
		//front = (front + 1) % size;	δεν εχουμε μεγεθος
		//bufferFull = false;
		if (counter==0) 
		{
			//bufferEmpty = true;
			System.out.println("The buffer is empty");
		}
		//buffer was full
		//if (counter == size-1) 
		
		notifyAll(); //το ιδιο με επάνω 
		return data;
	}
}

	
			
	
