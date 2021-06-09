package guis;

import daos.HibernateUtil;
import org.hibernate.exception.JDBCConnectionException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class DBLogIn extends JFrame {
    private JPanel logInPanel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JLabel usernameLabel;
    private JPanel mainPanel;
    private JPanel instructPanel;
    private JTextField urlTextField;

    public DBLogIn() {
        this.setTitle("Database Log In");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,400);
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbURL = urlTextField.getText();
                if (dbURL.equals("")) dbURL = null;
                String username = usernameTextField.getText();
                if (username.equals("")) username = null;
                char[] temp = passwordField.getPassword();
                String password = new String(temp);
                if (password.equals("")) password = null;

                HibernateUtil.buildSessionFactory(dbURL, username, password);
                JFrame logIn = new LogIn();
                logIn.setVisible(true);
                getFrame().dispose();
            }
        });
    }

    public JFrame getFrame() {
        return this;
    }
}
