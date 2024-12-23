package lab12;

class NumberPrinter extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Number: " + i);
            try {
                Thread.sleep(500); //ADD DELY
            } catch (InterruptedException e) {
                System.out.println("Number thread interrupted: " + e.getMessage());
            }
        }
    }
}

//SQUARES OF VALUES
class SquarePrinter implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Square: " + (i * i));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Square thread interrupted: " + e.getMessage());
            }
        }
    }
}

public class Lab14_A {
    public static void main(String[] args) {
        //CREATE AND START FIRST THREAD
        Thread numberThread = new NumberPrinter();
        numberThread.start();

        // SECOND THREAD
        Thread squareThread = new Thread(new SquarePrinter());
        squareThread.start();
        System.out.println("Main thread ends execution.");
    }
}
