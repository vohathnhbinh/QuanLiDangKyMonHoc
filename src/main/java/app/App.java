package app;

import additionals.Account_Info;
import additionals.Role;
import daos.*;
import additionals.Gender;
import guis.LogIn;
import models.*;
import models.Class;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
        HibernateUtil.getSessionFactory();
        JFrame frame = new LogIn();
        frame.setVisible(true);

//        Staff staff = new Staff();
//        staff.setRole(Role.STAFF);
//        staff.setFullname("XYZ");
//        StaffDao.add(staff);
    }
}
