package guis;

import additionals.Account_Info;
import daos.StaffDao;
import daos.StudentDao;
import models.Staff;
import models.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountSettingAlt extends JDialog {

    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JTextField fullnameTextField;
    private JButton editusernameButton;
    private JButton editfullnameButton;
    private JTextField editusernameTF;
    private JButton saveusernameButton;
    private JTextField editfullnameTF;
    private JButton savefullnameButton;
    private JButton resetpasswordButton;
    private JLabel usernameLabel;
    private JLabel fullnameLabel;
    private JLabel passwordLabel;

    public AccountSettingAlt(Object user) {
        this.setTitle("Account Setting");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(800,600);

        Student student = null;
        Staff staff = null;
        if (Student.class == user.getClass())
            student = (Student) user;
        else
            staff = (Staff) user;

        final String username = (student != null) ? student.getUsername() : staff.getUsername();
        String fullname = (student != null) ? student.getFullname() : staff.getFullname();
        usernameTextField.setText(username);
        fullnameTextField.setText(fullname);
        usernameTextField.setEditable(false);
        fullnameTextField.setEditable(false);

        editusernameTF.setVisible(false);
        editfullnameTF.setVisible(false);
        saveusernameButton.setVisible(false);
        savefullnameButton.setVisible(false);

        editusernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editusernameTF.isVisible()) {
                    editusernameTF.setVisible(false);
                    saveusernameButton.setVisible(false);
                } else {
                    editusernameTF.setVisible(true);
                    saveusernameButton.setVisible(true);
                }
            }
        });
        final Student finalStudent = student;
        final Staff finalStaff = staff;
        saveusernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changedUsername = editusernameTF.getText();
                if (changedUsername.length() == 0) {
                    JOptionPane.showMessageDialog(getDialog(), "Username trống!");
                    return;
                }
                Account_Info accountInfo = new Account_Info();
                accountInfo.username = changedUsername;
                if (finalStudent != null) {
                    JOptionPane.showMessageDialog(getDialog(), StudentDao.changeInfo(finalStudent, accountInfo));
                    usernameTextField.setText(finalStudent.getUsername());
                } else {
                    JOptionPane.showMessageDialog(getDialog(), StaffDao.changeInfo(finalStaff, accountInfo));
                    usernameTextField.setText(finalStaff.getUsername());
                }
            }
        });
        editfullnameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editfullnameTF.isVisible()) {
                    editfullnameTF.setVisible(false);
                    savefullnameButton.setVisible(false);
                } else {
                    editfullnameTF.setVisible(true);
                    savefullnameButton.setVisible(true);
                }
            }
        });
        savefullnameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changedFullname = editfullnameTF.getText();
                if (changedFullname.length() == 0) {
                    JOptionPane.showMessageDialog(getDialog(), "Họ tên trống!");
                    return;
                }
                Account_Info accountInfo = new Account_Info();
                accountInfo.fullname = changedFullname;
                if (finalStudent != null) {
                    JOptionPane.showMessageDialog(getDialog(), StudentDao.changeInfo(finalStudent, accountInfo));
                    fullnameTextField.setText(finalStudent.getFullname());
                } else {
                    JOptionPane.showMessageDialog(getDialog(), StaffDao.changeInfo(finalStaff, accountInfo));
                    fullnameTextField.setText(finalStaff.getFullname());
                }
            }
        });
        resetpasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account_Info accountInfo = new Account_Info();
                if (finalStudent != null) {
                    accountInfo.old_password = finalStudent.getPassword();
                    accountInfo.new_password = finalStudent.getStudent_number();
                    accountInfo.retype_password = finalStudent.getStudent_number();
                    JOptionPane.showMessageDialog(getDialog(), StudentDao.changeInfo(finalStudent, accountInfo));
                } else {
                    accountInfo.old_password = finalStaff.getPassword();
                    accountInfo.new_password = finalStaff.getStaff_number();
                    accountInfo.retype_password = finalStaff.getStaff_number();
                    JOptionPane.showMessageDialog(getDialog(), StaffDao.changeInfo(finalStaff, accountInfo));
                }
            }
        });
    }

    public JDialog getDialog() {
        return this;
    }
}
