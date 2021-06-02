package daos;

import models.Student;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDao {
    private static List<Student> students = null;
    public static List<Student> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Student";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            students = ss.createQuery(hql).list();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }

        return students;
    }

    public static Student findOne(String cond, String field) {
        Student student = null;
        Transaction trans = null;
        try {
            String hql = "FROM Student WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = ss.createQuery(hql).setParameter(field, cond);
            student = (Student) query.uniqueResult();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return student;
    }

    public static void add(Student student) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(student);
            ss.flush();
            ss.refresh(student);

            String studentNumber = student.getStudent_number();
            student.setUsername(studentNumber);
            student.setPassword(studentNumber);
            ss.update(student);
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }
}
