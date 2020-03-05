/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.AccountUser;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author oujia
 */public class UserListModel extends AbstractListModel {
    
    private List<AccountUser> liUsers = new ArrayList();
    
    public void addUser(AccountUser user)
    {
        liUsers.add(user);
        fireContentsChanged(this, 0, liUsers.size() - 1);
    }
    
    @Override
    public int getSize() {
        return liUsers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return liUsers.get(index);
    }
    
    public void clear()
    {
        liUsers = new ArrayList<>();
        fireContentsChanged(this, 0, liUsers.size() - 1);
    }
    
}