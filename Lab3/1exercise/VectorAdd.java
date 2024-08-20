/* Vector Addition a = b + c                       */

class VectorAdd
{
  public static void main(String args[])
  {

    // Τοπικές στην main size,numThreads,block,from,to,threads,start,elapsedTimeMillis
    int size = 1000;
    int numThreads = Runtime.getRuntime().availableProcessors();
    
    double[] a = new double[size];
    double[] b = new double[size];
    double[] c = new double[size];
    for(int i = 0; i < size; i++) {
        a[i] = 0.0;
		b[i] = 1.0;
        c[i] = 0.5;
    }

    
    long start = System.currentTimeMillis();


    int block = size / numThreads;
    int from = 0;
    int to = 0;

    VectorAddThread threads[] = new VectorAddThread[numThreads];

    for(int i=0; i<numThreads; i++){
        from = i * block;
        to = i * block + block;
        if(i == (numThreads - 1))
            to = size;
        threads[i] = new VectorAddThread(a, b, c, from, to);
        threads[i].start();
    }

    for(int i=0; i<numThreads; i++){
        try{
            threads[i].join();
        } catch (InterruptedException e){
        }
    }

   
    long elapsedTimeMillis = System.currentTimeMillis() - start;
    System.out.println("Time in ms = " + elapsedTimeMillis);

		
    

    /* for debugging 
    for(int i = 0; i < size; i++) 
        System.out.println(a[i]); */
  }
}

class VectorAddThread extends Thread {

    //μεταβλητές κλάσεων a,b,c,myfrom,myto επίσης περνούς ώς ορίσματα τιμής στα νήματα 
    private double[] a;
    private double[] b;
    private double[] c;
    private int myfrom;
    private int myto;

    //constructor
    public VectorAddThread(double[] a, double[] b, double[] c, int from, int to){
        this.a = a;
        this.b = b;
        this.c = c;
        myfrom = from;
        myto = to;
    }
    
        public void run(){
            for (int i = myfrom; i < myto; i++)
                a[i] = b[i] + c[i];
        }
    

}
