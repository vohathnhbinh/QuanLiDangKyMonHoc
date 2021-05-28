package daos;

import models.Student;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDao {
    private static List<Student> students = null;
    public static List<Student> getAll() {
        try {
            String hql = "FROM Student";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            Transaction trans = ss.beginTransaction();
            students = ss.createQuery(hql).list();
        } catch (HibernateError err) {
            System.err.println(err);
        }

        return students;
    }

    public static Student findOne(String cond) {
        Student student = null;
        try {
            String hql = "FROM Student WhERE ";
            hql += cond;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            Transaction trans = ss.beginTransaction();
            student = (Student) ss.createQuery(hql).uniqueResult();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return student;
    }
}
