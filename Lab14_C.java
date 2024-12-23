package lab12;

import java.util.concurrent.CopyOnWriteArrayList;

class ListTask implements Runnable {
    private final CopyOnWriteArrayList<String> sharedList;
    private final String threadName;

    public ListTask(CopyOnWriteArrayList<String> sharedList, String threadName) {
        this.sharedList = sharedList;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        // Add items
        for (int i = 1; i <= 5; i++) {
            String item = threadName + "-Item" + i;
            sharedList.add(item);
            System.out.println(threadName + " added: " + item);

            // Read 
            System.out.println(threadName + " sees: " + sharedList);

            try {
                Thread.sleep(100); // DELAY
            } catch (InterruptedException e) {
                System.out.println(threadName + " interrupted: " + e.getMessage());
            }
        }
    }
}

public class Lab14_C {
    public static void main(String[] args) {
        // Use CopyOnWriteArrayList to ensure safe con-current access
        CopyOnWriteArrayList<String> sharedList = new CopyOnWriteArrayList<>();

        // Create multiple threads that access the shared list
        Thread thread1 = new Thread(new ListTask(sharedList, "Thread-1"));
        Thread thread2 = new Thread(new ListTask(sharedList, "Thread-2"));
        Thread thread3 = new Thread(new ListTask(sharedList, "Thread-3"));

        // Start
        thread1.start();
        thread2.start();
        thread3.start();

        // wait
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Final state of the shared list
        System.out.println("Final state of the shared list: " + sharedList);
    }
}
