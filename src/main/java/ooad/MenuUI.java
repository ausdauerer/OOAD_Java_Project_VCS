package ooad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.html.ListView;

public class MenuUI  {
    public MenuUI() throws SQLException{
        App app=App.getInstance();
        final JFrame f = new JFrame();
        
        JButton Create_repo = new JButton("Create");
        JButton Delete_repo = new JButton("Delete");
        JButton Open_repo = new JButton("Open");
        //call arralist for repo's
        DefaultListModel<String> list = new DefaultListModel<>();
        
        ArrayList<String> l=app.displayRepositories();

        for(int i=0;i<l.size();i++){
            list.addElement(l.get(i));
        }  
        final JList<String> listview = new JList<>(list); 


        listview.setBounds(20, 10, 400, 400);
        Create_repo.setBounds(450, 80,100, 30);
        Delete_repo.setBounds(450,140,100,30);
        Open_repo.setBounds(450,200,100,30);

        //Creating a Repo
        Create_repo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e){
                new Create();
            }
        });


        //Deleting a Repo
        Delete_repo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String path = listview.getSelectedValue();
                //call del func and pass path STR
                JFrame del = new JFrame();
                JOptionPane.showMessageDialog(del, "message", "Delete Repo", JOptionPane.PLAIN_MESSAGE);
            }
        });

        //Opening a Repo
        Open_repo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    new RepositoryUI(listview.getSelectedValue());
                } catch (ClassNotFoundException | SQLException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,450);
        f.add(listview); 
        f.add(Create_repo);
        f.add(Delete_repo);
        f.add(Open_repo);
        f.setLayout(null);
        f.setVisible(true);
    }
}