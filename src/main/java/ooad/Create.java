package ooad;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Create {
    public Create(){
        JFrame creating = new JFrame("Creating repo");
        String path=JOptionPane.showInputDialog(creating, "Path for the new repo");
        //JOptionPane.showMessageDialog(creating, path);

    }
}