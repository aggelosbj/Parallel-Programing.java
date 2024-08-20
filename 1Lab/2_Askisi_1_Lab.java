public class Main {
    public static void main(String[] args){
     for(int i=1; i<=10; i++){ // Προσθέτουμε μια for
        MyThread1 thread1 = new MyThread1("Thread1");
        MyThread2 thread2 = new MyThread2("Thread2");
        
        thread1.start();
        new Thread(thread2).start(); 
      }
        
   }
}

class MyThread1 extends Thread{
    public MyThread1(String name){
        super(name);
    }
    
    public void run() {
        for (int i=0; i<5; i++){
            System.out.println("MyThread1: " + i); 
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyThread2 implements Runnable {
    private String name;

    public MyThread2(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("MyThread2: " + i);
            try {
                Thread.sleep(150); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
