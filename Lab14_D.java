package lab12;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

class BankAccount {
    private final AtomicInteger balance;

    public BankAccount(int initialBalance) {
        this.balance = new AtomicInteger(initialBalance);
    }

    // Deposit(safe iwth AtomicInteger)
    public void deposit(int amount) {
        if (amount > 0) {
            balance.addAndGet(amount);
            //debugh here
            System.out.println(Thread.currentThread().getName() + " deposited: " + amount + ", Balance: " + balance.get());
        }
    }

    // Withdraw method (safe with AtomicInteger)
    public void withdraw(int amount) {
        if (amount > 0 && balance.get() >= amount) {
            balance.addAndGet(-amount);
            System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + ", Balance: " + balance.get());
        } else {
            System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + " (Insufficient funds)");
        }
    }

    // Get current balance
    public int getBalance() {
        return balance.get();
    }
}

class ClientTask implements Runnable {
    private final BankAccount account;
    private final Random random = new Random();

    public ClientTask(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int transactionAmount = random.nextInt(100) + 1; // Random int between 1 and 100
            if (random.nextBoolean()) {
                account.deposit(transactionAmount);
            } else {
                account.withdraw(transactionAmount);
            }

            try {
                Thread.sleep(100); // DELAY
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted: " + e.getMessage());
            }
        }
    }
}

public class Lab14_D {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(500); // INITILAIZATION 

        // Create 
        Thread client1 = new Thread(new ClientTask(account), "Client-1");
        Thread client2 = new Thread(new ClientTask(account), "Client-2");
        Thread client3 = new Thread(new ClientTask(account), "Client-3");

        // Start 
        client1.start();
        client2.start();
        client3.start();

        // Wait for all threads to complete
        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        // Final balance
        System.out.println("Final Account Balance: " + account.getBalance());
    }
}
