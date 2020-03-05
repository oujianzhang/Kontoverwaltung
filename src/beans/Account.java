/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.swing.JLabel;

/**
 *
 * @author oujia
 */
public class Account {
    private int balance;
    private JLabel lbAccount;

    public Account(int balance, JLabel lbAccount) {
        this.balance = balance;
        this.lbAccount = lbAccount;
    }

    public int getBalance() {
        return balance;
    }
    
    public void setBalance(int balance)
    {
        this.balance = balance;
        this.lbAccount.setText(String.format("%.2f €", balance));
    }
}
