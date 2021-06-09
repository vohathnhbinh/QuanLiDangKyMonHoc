package daos;

import models.Course;

import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

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
            ss.close();
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
            ss.close();
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
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static void remove(Course course) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            ss.delete(course);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static int myParseInt(String credit) {
        try {
            return Integer.parseInt(credit);
        } catch (NumberFormatException err) {
            return 0;
        }
    }

    public static List<Course> searchCourse(String info) {
        Transaction trans = null;
        List<Course> newCourses = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            courses = CourseDao.getAll();
            String criteria = ".*" + info + ".*";
            newCourses = courses.stream()
                    .filter(course -> course.getSubject().getSubject_number().matches(criteria)
                    || course.getSubject().getSubject_name().matches(criteria)
                    || course.getSubject().getCredit_amount() == myParseInt(info)
                    || course.getTeacher().getTeacher_name().matches(criteria)
                    || course.getClassroom().matches(criteria)
                    || course.getDate_of_week().getDate().matches(criteria)
                    || course.getShift().getShift_time().matches(criteria))
                    .collect(Collectors.toList());

            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return newCourses;
    }
}
