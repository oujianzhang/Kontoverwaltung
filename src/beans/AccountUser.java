/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author oujia
 */
public class AccountUser implements Runnable {

    private String name;
    private JTextArea taLogOutput;
    private Account commonAccount;
    private boolean withdraw;
    private double amount;
    private Random rand = new Random();

    public AccountUser(String name, JTextArea taLogOutput, Account commonAccount) {
        this.name = name;
        this.taLogOutput = taLogOutput;
        this.commonAccount = commonAccount;
    }

    @Override
    public void run() {
        synchronized (commonAccount) {

            for (int i = 0; i < 10; i++) {
                
                try {
                    Thread.sleep(rand.nextInt(1000) + 1);
                } catch (InterruptedException ex) {
                    System.out.println(name + " ;sleep");
                }
                
                withdraw = rand.nextBoolean();
                amount = rand.nextInt(50) + 1;
                if (commonAccount.getBalance() < amount) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        System.out.println(name + " interrupted.");
                    }
                }

                if (withdraw) {
                    taLogOutput.append(String.format("%s withdraws: %.2f €\n", name, amount));
                    commonAccount.setBalance(commonAccount.getBalance() - amount);
                    
                } else {
                    taLogOutput.append(String.format("%s deposts: %.2f €\n", name, amount));
                    commonAccount.setBalance(commonAccount.getBalance() + amount);
                    notifyAll();
                }
            }

            System.out.println(name + " has finished.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
