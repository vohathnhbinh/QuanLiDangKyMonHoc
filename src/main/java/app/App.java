package app;

import additionals.Account_Info;
import daos.ClassDao;
import daos.StudentDao;
import daos.CourseDao;
import additionals.Gender;
import models.Course;
import models.Student;
import models.Class;
import models.Student_Course;

import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
        Course course = CourseDao.findOne("1", "course_id");
        Student student = StudentDao.findOne("2", "user_id");
        System.out.println(StudentDao.interactCourse(student, course, false));
        Set<Student_Course> scs = student.getCourses();
        for (Student_Course sc : scs) {
            System.out.println(sc.getId().getUser_id());
        }

    }
}
