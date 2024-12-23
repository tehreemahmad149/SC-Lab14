package lab12;

class SharedCounter {
    private int counter = 0;

    // increment the shared counter
    public synchronized void increment() {
        counter++;
    }

    // getter
    public int getCounter() {
        return counter;
    }
}

// shared counter implemented
class CounterIncrementer implements Runnable {
    private final SharedCounter sharedCounter;

    public CounterIncrementer(SharedCounter sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            sharedCounter.increment();
            try {
                Thread.sleep(10); // DELAY
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}

public class Lab14_B {
    public static void main(String[] args) {
        SharedCounter sharedCounter = new SharedCounter();

        // Creating 
        Thread thread1 = new Thread(new CounterIncrementer(sharedCounter));
        Thread thread2 = new Thread(new CounterIncrementer(sharedCounter));
        Thread thread3 = new Thread(new CounterIncrementer(sharedCounter));
        
        //start

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // final counter val
        System.out.println("Final Counter Value: " + sharedCounter.getCounter());
    }
}
