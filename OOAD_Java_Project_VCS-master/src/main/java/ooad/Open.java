import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Open {
    public Open(String selc_file){
        JFrame open = new JFrame();
        open.setSize(600,600);
        open.setLayout(null);
        open.setVisible(true);

        DefaultListModel<String> files = new DefaultListModel<>();
        files.addElement(selc_file);
        final JList<String> scroll = new JList<>(files); 

        JButton Commit = new JButton("Commit");
        JButton Roll_back = new JButton("Roll Back");
        JButton Show_changes = new JButton("Show Changes");
        scroll.setBounds(10, 20, 400, 580);
        Commit.setBounds(420,80,130,30);
        Roll_back.setBounds(420,150,130,30);
        Show_changes.setBounds(420,210,130,30);
        
        Commit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //call commit func
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Commit done", "commit", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });

        Roll_back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //call commit func
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Roll Back done", "Roll Back", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });

        Show_changes.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //call commit func
                JFrame f = new JFrame();
                //call show_changes
                JOptionPane.showMessageDialog(f,/*Changes str*/"", "Changes", JOptionPane.PLAIN_MESSAGE);
                
            }
        });

        open.add(scroll);
        open.add(Commit);
        open.add(Roll_back);
        open.add(Show_changes);


    }
}