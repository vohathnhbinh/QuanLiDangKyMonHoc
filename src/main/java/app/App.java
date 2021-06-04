package app;

import additionals.Account_Info;
import daos.*;
import additionals.Gender;
import guis.LogIn;
import models.*;
import models.Class;

import javax.swing.*;
import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
        HibernateUtil.getSessionFactory();
        JFrame frame = new LogIn();
        frame.setVisible(true);
    }
}
