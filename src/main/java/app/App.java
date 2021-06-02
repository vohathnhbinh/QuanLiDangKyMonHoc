package app;

import additionals.Account_Info;
import daos.ClassDao;
import daos.StudentDao;
import additionals.Gender;
import models.Student;
import models.Class;

public class App 
{
    public static void main( String[] args )
    {
        Student student = StudentDao.findOne("SV002", "username");
        Account_Info accountInfo = new Account_Info();
        accountInfo.username = "nhokngoknghek";
        String msg = StudentDao.changeInfo(student, accountInfo);
        System.out.println(msg);
    }
}
