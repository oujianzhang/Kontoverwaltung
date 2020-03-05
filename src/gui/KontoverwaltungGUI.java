/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.AccountUser;
import bl.UserListModel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void initUser() {
        
        JList liUser = new JList(ulm);

        JPopupMenu pmUser = new JPopupMenu();
        
        //Initialize Add User Menue Item
        JMenuItem miAddUser = new JMenuItem("add user");
        pmUser.add(miAddUser);
        miAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Enter username");
                ulm.addUser();
            }
        });
        
        //Initialize Account Test
        JMenuItem miAddAccountTest = new JMenuItem("perform account test");
        pmUser.add(miAddAccountTest);
        miAddAccountTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        } );
        
        
        liUser.setComponentPopupMenu(pmUser);

        paUser.add(liUser, BorderLayout.CENTER);
        paUser.setPreferredSize(new Dimension(60, HEIGHT));
    }

    public void initLogOutput() {
        taLogOutput = new JTextArea();
        JScrollPane scrollLogOutput = new JScrollPane(taLogOutput);
        paLogOutput.add(scrollLogOutput, BorderLayout.CENTER);
        
        JMenuItem miCreateAccount = new JMenuItem("create new account");
        JPopupMenu pmCreateAccount = new JPopupMenu();
        pmCreateAccount.add(miCreateAccount);
        taLogOutput.add(pmCreateAccount);
        miCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulm.clear();
                taLogOutput.setText("");
            }
        });
        
    }

    public void initAccount() {
        JLabel lbAccount = new JLabel("50,00 Euro");
        paAccount.add(lbAccount, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        KontoverwaltungGUI gui = new KontoverwaltungGUI();
        gui.setVisible(true);
    }
}
