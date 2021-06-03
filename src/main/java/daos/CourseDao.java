package daos;

import models.Course;
import models.Staff;
import models.Student;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDao {
    private static List<Course> courses = null;
    public static List<Course> getAll() {
        Transaction trans = null;
        try {
            String hql = "FROM Course";
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            courses = ss.createQuery(hql).list();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return courses;
    }

    public static Course findOne(String cond, String field) {
        Course course = null;
        Transaction trans = null;
        try {
            String hql = "FROM Course WHERE ";
            hql += field + " = " + ":" + field;
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
            course = (Course) query.uniqueResult();
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return course;
    }

    public static void add(Course course) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            int id = (int) ss.save(course);
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }
}
