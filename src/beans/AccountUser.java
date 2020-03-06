/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
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
        for (int i = 0; i < 10; i++) {
            
            synchronized (commonAccount) {
                withdraw = rand.nextBoolean();
                amount = rand.nextInt(41) + 10;
                System.out.println("calc");
                if(withdraw && commonAccount.getBalance() < amount)
                {
                    System.out.println("negative");
                    try {
                        commonAccount.wait(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AccountUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i--;
                    continue;
                }
                
                if (withdraw) {
                    System.out.println("withdraw");
                    try {
                        EventQueue.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                taLogOutput.append(String.format("%s withdraws: %.2f €\n", name, amount));
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException ex) {
                        Logger.getLogger(AccountUser.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    commonAccount.setBalance(commonAccount.getBalance() - amount);
                } else {
                    System.out.println("deposit");
                    try {
                        EventQueue.invokeAndWait(() -> {
                            taLogOutput.append("finished waiting\n");
                            taLogOutput.append(String.format("%s deposits: %.2f €\n", name, amount));
                        });
                    } catch (InterruptedException | InvocationTargetException ex) {
                        System.out.println("AHHHHHHHHH");
                    }

                    commonAccount.setBalance(commonAccount.getBalance() + amount);
                    commonAccount.notifyAll();
                }

            }
            System.out.println("put to slep");
            try {
                Thread.sleep(rand.nextInt(1000) + 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(AccountUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        taLogOutput.append(">>> " + name + " has finished." + " <<<\n");
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
