package ooad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class RepositoryUI{
    public RepositoryUI(String selc_file) throws ClassNotFoundException, SQLException, IOException{
        JFrame open = new JFrame();
        App app=App.getInstance();
        app.OpenRepository(selc_file);
        open.setSize(600,600);
        open.setLayout(null);
        open.setVisible(true);

        DefaultListModel<String> files = new DefaultListModel<>();
        ArrayList<String> l=app.repo.getFilesInRepo();

        for(int i=0;i<l.size();i++){
            files.addElement(l.get(i));
        }

        JList<String> scroll = new JList<>(files); 

        JButton Commit = new JButton("Commit");
        JButton Roll_back = new JButton("Roll Back");
        JButton Show_changes = new JButton("Show Changes");
        JButton open_with_vscode = new JButton("Open with VScode");
        scroll.setBounds(10, 20, 400, 580);
        Commit.setBounds(420,80,130,30);
        Roll_back.setBounds(420,150,130,30);
        Show_changes.setBounds(420,210,130,30);
        open_with_vscode.setBounds(420,270,130,30);
        
        Commit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //call commit func
                App app=App.getInstance();
                try {
                    app.repo.commitRepository();
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
        });

        Roll_back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //call commit func
                    App app=App.getInstance();
                    try {
                        app.repo.rollback();;
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Roll Back done", "Roll Back", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });

        Show_changes.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App app=App.getInstance();
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
        });

        open_with_vscode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                App app=App.getInstance();
                app.repo.openInVSCode();
            }
        });

        open.add(scroll);
        open.add(Commit);
        open.add(Roll_back);
        open.add(Show_changes);
        open.add(open_with_vscode);


    }
}