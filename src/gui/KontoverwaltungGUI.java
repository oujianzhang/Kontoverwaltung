/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Account;
import beans.AccountUser;
import bl.UserListModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author oujia
 */
public class KontoverwaltungGUI extends JFrame {

    /*
    * TO DO:
    * Make making USER possible only when Account has been created!
    * Connect perform account test event to UserListModel then AccountUser
    * via Thread
    * in Account User implement run method
     */
    Container cont = getContentPane();
    UserListModel ulm = new UserListModel();

    JPanel paUser = new JPanel(new BorderLayout());
    JPanel paLogOutput = new JPanel(new BorderLayout());
    JPanel paAccount = new JPanel(new BorderLayout());
    JTextArea taLogOutput;

    private Account acc;
    private JLabel lbAccount = new JLabel();

    public KontoverwaltungGUI() throws HeadlessException {
        initComponents();
        this.setSize(380, 310);
        this.setLocationRelativeTo(null);
    }

    public void initComponents() {
        cont.setLayout(new BorderLayout());
        cont.add(paUser, BorderLayout.WEST);
        cont.add(paLogOutput, BorderLayout.CENTER);
        cont.add(paAccount, BorderLayout.SOUTH);

        paUser.setBorder(new TitledBorder("User"));
        paLogOutput.setBorder(new TitledBorder("Log-output"));
        paAccount.setBorder(new TitledBorder("Account"));

        initUser();
        initLogOutput();
        initAccount();
    }
    
    JList liUser = new JList(ulm);

    public void initUser() {

        JScrollPane scUser = new JScrollPane(liUser);

        JPopupMenu pmUser = new JPopupMenu();

        //Initialize Add User Menue Item
        JMenuItem miAddUser = new JMenuItem("add user");
        pmUser.add(miAddUser);
        miAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Enter username");
                if (acc != null) {
                    ulm.addUser(new AccountUser(username, taLogOutput, acc));
                } else {
                    JOptionPane.showMessageDialog(null, "You need to create an account first. Do so by right-clicking the Log-Output window.");
                }
            }
        });

        //Initialize Account Test
        JMenuItem miAddAccountTest = new JMenuItem("perform account test");
        pmUser.add(miAddAccountTest);
        miAddAccountTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] users = liUser.getSelectedIndices();
                for (int i = 0; i < users.length; i++) {
                    AccountUser currUser = ulm.getElementAt(users[i]);
                    Thread t = new Thread(currUser);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(KontoverwaltungGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        liUser.setComponentPopupMenu(pmUser);
        paUser.add(scUser, BorderLayout.CENTER);
    }

    public void initLogOutput() {
        taLogOutput = new JTextArea();
        taLogOutput.setInheritsPopupMenu(true);
        JScrollPane scrollLogOutput = new JScrollPane(taLogOutput);
        paLogOutput.add(scrollLogOutput, BorderLayout.CENTER);

        JMenuItem miCreateAccount = new JMenuItem("create new account");
        JPopupMenu pmCreateAccount = new JPopupMenu();
        pmCreateAccount.add(miCreateAccount);
        taLogOutput.setComponentPopupMenu(pmCreateAccount);

        miCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulm.clear();
                taLogOutput.setText("");
                acc = new Account(50, lbAccount);
                acc.setBalance(50);
            }
        });

    }

    public void initAccount() {
        paAccount.add(lbAccount, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        KontoverwaltungGUI gui = new KontoverwaltungGUI();
        gui.setVisible(true);
    }
}
