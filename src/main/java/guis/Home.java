package guis;

import models.Staff;
import models.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{
    private JPanel mainPanel;
    private JButton accountSettingButton;
    private JButton LOGOUTButton;
    private JLabel welcome;

    public Home(Object user) {
        this.setTitle("Log In");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manage Courses");
        this.setSize(800, 600);
        Student student = null;
        Staff staff = null;
        if (user.getClass() == Student.class)
            student = (Student) user;
        else
            staff = (Staff) user;
        String fullname = (student != null)?student.getFullname():staff.getFullname();
        welcome.setText(welcome.getText() + fullname);
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new LogIn();
                frame.setVisible(true);
                getFrame().dispose();
            }
        });
        final Student finalStudent = student;
        final Staff finalStaff = staff;
        accountSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new AccountSetting(finalStudent, finalStaff);
                dialog.setModal(true);
                dialog.setVisible(true);
            }
        });
    }
    public JFrame getFrame() {
        return this;
    }
}
