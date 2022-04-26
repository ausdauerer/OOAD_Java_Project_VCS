package ooad;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import ooad.MenuUI;

public class Controller implements ActionListener{
    public App app;
    MenuUI menuUI;
    RepositoryUI repoUI;
    public static Controller controller =null;

    public static Controller getInstance() throws SQLException{
        if(controller==null){
            controller=new Controller();
        }
        return(controller);
    }


    
    public Controller() throws SQLException{
        app = new App();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

        //Create a Repo
        if((e.getActionCommand()).compareTo("Create")==0){
                try {
                    new Create();
                } catch (SQLException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                DefaultListModel<String> list = new DefaultListModel<>();
                ArrayList<String> l;
                try {

                    l = app.displayRepositories();
                    for(int i=0;i<l.size();i++){
                        list.addElement(l.get(i));
                    }
                    menuUI.menuList.setModel(list);

                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }               
        }

        //Delete a Repo
        else if((e.getActionCommand()).compareTo("Delete")==0){
            String path = menuUI.menuList.getSelectedValue();
            try {
                //delete repo
                app.deleteRepository(path);
                //update listView
                DefaultListModel<String> list = new DefaultListModel<>();
                ArrayList<String> l;
                l = app.displayRepositories();
                    for(int i=0;i<l.size();i++){
                        list.addElement(l.get(i));
                    }
                    menuUI.menuList.setModel(list);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            JFrame del = new JFrame();
            JOptionPane.showMessageDialog(del, "message", "Delete Repo", JOptionPane.PLAIN_MESSAGE);
        }

        //Opening a Repo
        else if((e.getActionCommand()).compareTo("Open")==0){
            try {
                repoUI = new RepositoryUI();
                app.OpenRepository(menuUI.menuList.getSelectedValue());
                DefaultListModel<String> list = new DefaultListModel<>();
                ArrayList<String> l=app.getFilesArrayList();
                for(int i=0;i<l.size();i++){
                    list.addElement(l.get(i));
                }  
                repoUI.repoList.setModel(list);
                
            } catch (ClassNotFoundException | SQLException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        //Shows changes done inside Repo
        else if((e.getActionCommand()).compareTo("Show Changes")==0){
            String changes="";
            try {
                changes=app.repo.getChangesString();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            JFrame f = new JFrame();
            //call show_changes
            JOptionPane.showMessageDialog(f,changes, "Changes", JOptionPane.PLAIN_MESSAGE);
            
        }

        //Commit changes done in Repo
        else if((e.getActionCommand()).compareTo("Commit")==0){
                try {
                    app.commitChanges();
                    DefaultListModel<String> list = new DefaultListModel<>();
        
                    ArrayList<String> l;
                    try {
                        l = app.getFilesArrayList();
                        for(int i=0;i<l.size();i++){
                            list.addElement(l.get(i));
                        } 
                        repoUI.repoList.setModel(list);
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                   

                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Commit done", "commit", JOptionPane.INFORMATION_MESSAGE);
                return;
        }

        //Roll back changes done to Repo
        else if((e.getActionCommand()).compareTo("Roll Back")==0){
                    try {
                        app.repo.rollback();
                        DefaultListModel<String> list = new DefaultListModel<>();
        
                        ArrayList<String> l=app.getFilesArrayList();

                        for(int i=0;i<l.size();i++){
                            list.addElement(l.get(i));
                        } 
                        repoUI.repoList.setModel(list);
                    }
                    catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } 
                    catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } 
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } 
                    catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Roll Back done", "Roll Back", JOptionPane.INFORMATION_MESSAGE);
        }

        //Open code in VS-Code
        else if((e.getActionCommand()).compareTo("Open with VScode")==0){
            app.repo.openInVSCode();            
        }

    }
    public static void main(String args[]) throws SQLException{
        Controller con=Controller.getInstance();
        con.menuUI = new MenuUI();
        DefaultListModel<String> list = new DefaultListModel<>();
        ArrayList<String> l=con.app.displayRepositories();
        for(int i=0;i<l.size();i++){
            list.addElement(l.get(i));
        }  
        con.menuUI.menuList.setModel(list);
    }

}
