package app;

import daos.ClassDao;
import daos.StudentDao;
import models.Gender;
import models.Student;
import models.Class;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        Class theirClass = ClassDao.findOne("18CTT2", "class_name");
        Student student = new Student("Nguyen Thi B", theirClass, Gender.FEMALE);
        StudentDao.add(student);
        System.out.println(student.getStudent_number());
    }
}
