package guis;

import additionals.Account_Info;
import daos.StaffDao;
import daos.StudentDao;
import models.Staff;
import models.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountSetting extends JDialog {

    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JTextField fullnameTextField;
    private JButton editusernameButton;
    private JButton editfullnameButton;
    private JTextField editusernameTF;
    private JButton saveusernameButton;
    private JTextField editfullnameTF;
    private JButton savefullnameButton;

    public AccountSetting(final Student student, final Staff staff) {
        this.setTitle("Account Setting");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(800,600);

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
                editusernameTF.setVisible(true);
                saveusernameButton.setVisible(true);
            }
        });
        saveusernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changedUsername = editusernameTF.getText();
                Account_Info accountInfo = new Account_Info();
                accountInfo.username = changedUsername;
                if (student != null) {
                    JOptionPane.showMessageDialog(getDialog(), StudentDao.changeInfo(student, accountInfo));
                    usernameTextField.setText(student.getUsername());
                } else {
                    JOptionPane.showMessageDialog(getDialog(), StaffDao.changeInfo(staff, accountInfo));
                    usernameTextField.setText(staff.getUsername());
                }
            }
        });
    }

    public JDialog getDialog() {
        return this;
    }
}
