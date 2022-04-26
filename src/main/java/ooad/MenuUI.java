package ooad;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.html.ListView;


public class MenuUI  {
    public JList<String> menuList; 
    public JFrame f;
    public JButton Create_repo;
    public JButton Delete_repo;
    public JButton Open_repo;
    Controller controller;
    App app;

    public MenuUI() throws SQLException{
        f = new JFrame();
        Create_repo = new JButton("Create");
        Delete_repo = new JButton("Delete");
        Open_repo = new JButton("Open");
        menuList=new JList<>();
        controller = Controller.getInstance();



        menuList.setBounds(20, 10, 400, 400);
        Create_repo.setBounds(450, 80,100, 30);
        Delete_repo.setBounds(450,140,100,30);
        Open_repo.setBounds(450,200,100,30);

        //Creating a Repo
        Create_repo.addActionListener(controller);


        //Deleting a Repo
        Delete_repo.addActionListener(controller);

        //Opening a Repo
        Open_repo.addActionListener(controller);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,450);
        f.add(menuList); 
        f.add(Create_repo);
        f.add(Delete_repo);
        f.add(Open_repo);
        f.setLayout(null);
        f.setVisible(true);
    }
}