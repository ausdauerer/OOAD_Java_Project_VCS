package ooad;

import java.sql.SQLException;
import java.util.ResourceBundle.Control;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Create {
    public Create() throws SQLException{
        JFrame creating = new JFrame("Creating repo");
        String path=JOptionPane.showInputDialog(creating, "Path for the new repo");
        //JOptionPane.showMessageDialog(creating, path);
        Controller controller=Controller.getInstance();
        controller.app.CreateRepository(path);
    }
}