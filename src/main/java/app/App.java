package app;

import daos.StudentDao;
import models.Student;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        List<Student> students = StudentDao.getAll();
        for (Student student : students) {
            System.out.println(student.getStudent_number());
            System.out.println(student.getFullname());
            System.out.println(student.getUsername());
            System.out.println(student.getMyClass().getClass_name());
            System.out.println("\n");
        }
    }
}
