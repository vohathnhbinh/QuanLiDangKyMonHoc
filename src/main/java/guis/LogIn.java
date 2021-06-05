package guis;

import daos.StaffDao;
import daos.StudentDao;
import models.Staff;
import models.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {
    private JPanel panel1;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernametextField;
    private JPasswordField passwordField;
    private JButton loginBtn;

    public LogIn() {
        this.setTitle("Log In");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernametextField.getText();
                char[] temp = passwordField.getPassword();
                String password = new String(temp);
                Object user = null;
                user = StudentDao.logIn(username, password);
                if (user == null) {
                    user = StaffDao.logIn(username, password);
                    if (user == null)
                        JOptionPane.showMessageDialog(getFrame(), "Wrong username or password!");
                    else {
                        JOptionPane.showMessageDialog(getFrame(), "Logged in successful!");
                        JFrame home = new HomeStaff((Staff) user);
                        home.setVisible(true);
                        getFrame().dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(getFrame(), "Logged in successful!");
                    JFrame home = new HomeStudent((Student) user);
                    home.setVisible(true);
                    getFrame().dispose();
                }
            }
        });
    }

    public JFrame getFrame() {
        return this;
    }
}
