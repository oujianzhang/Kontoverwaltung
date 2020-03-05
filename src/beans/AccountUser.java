/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.swing.JTextArea;

/**
 *
 * @author oujia
 */
public class AccountUser implements Runnable{
    private String name;
    private JTextArea taLogOutput;
    private Account commonAccount;

    public AccountUser(String name, JTextArea taLogOutput, Account commonAccount) {
        this.name = name;
        this.taLogOutput = taLogOutput;
        this.commonAccount = commonAccount;
    }
    
    @Override
    public void run() {
        
    }
    
}
