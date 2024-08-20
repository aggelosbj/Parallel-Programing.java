/* Matrix Addition A = B + C                       */

class MatrixAdd
{
  public static void main(String args[])
  {

    //τοπικές μεταβλητές στην main είναι η size, start,numThreads,blockSize, threads
    //καθώς είναι προσβάσιμες μόνο από την μέθοδο main

    int size = 10;
    
    double[][] a = new double[size][size];
    double[][] b = new double[size][size];
    double[][] c = new double[size][size];
    for(int i = 0; i < size; i++) 
      for(int j = 0; j < size; j++) { 
        a[i][j] = 0.1;
		    b[i][j] = 0.3;
        c[i][j] = 0.5;
      }   

      long start = System.currentTimeMillis();

      int numThreads = Runtime.getRuntime().availableProcessors();
      int blockSize = size / numThreads;

      MatrixAddThread threads[] = new MatrixAddThread[numThreads];

      for(int i=0; i<numThreads; i++){
        int startRow = i * blockSize;
        int endRow = (i == numThreads -1) ? size : (i+1) * blockSize;
        threads[i] = new MatrixAddThread(a, b, c, startRow, endRow);
        threads[i].start();
      }

      for(int i=0; i<numThreads; i++){
        try{
            threads[i].join();
        } catch (InterruptedException e) {

        }
      }

      long elapsedTimeMillis = System.currentTimeMillis() - start;
      System.out.println("Time ms is = " + elapsedTimeMillis);
    
    

    // for debugging 
    for(int i = 0; i < size; i++) { 
      for(int j = 0; j < size; j++) 
        System.out.print(a[i][j]+" "); 
      System.out.println();
    }    
  }
}

class MatrixAddThread extends Thread {
  //Τοπικές μεταβλητές σε μέθοδο run 
    private double [][] a;
    private double [][] b;
    private double [][] c;
    private int startRow;
    private int endRow;
      // μεταβλητές αντικειμένων a,b,c είναι πίνακες αντικειμένων


      //Ορίσματα τιμής a,b,c,startRow,endRow περνούν ως ορίσματα τιμής
    public MatrixAddThread(double[][] a, double[][] b, double[][] c, int startRow, int endRow){
      this.a = a;
      this.b = b;
      this.c = c;
      this.startRow = startRow;
      this.endRow = endRow;
    }

    public void run(){
      for(int i= startRow; i<endRow; i++){
        for(int j=0; j<a[0].length; j++){
          a[i][j] = b[i][j] + c[i][j];
        }
      }
    }
}
