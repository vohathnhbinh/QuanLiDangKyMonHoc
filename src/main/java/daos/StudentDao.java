package daos;

import additionals.Account_Info;
import models.Class;
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

            Class theirClass = student.getMyClass();
            theirClass.setClass_size();
            theirClass.setMale_size();
            theirClass.setFemale_size();
            String studentNumber = student.getStudent_number();
            student.setUsername(studentNumber);
            student.setPassword(studentNumber);
            ss.update(student);
            ss.update(theirClass);
            trans.commit();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
    }

    public static String changeInfo(Student student, Account_Info account) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            if (account.fullname != null)
                student.setFullname(account.fullname);
            if (account.username != null) {
                if (account.username.length() < 6)
                    return "Username must have more than 6 characters!";
                Student anotherStudent = findOne(account.username, "username");
                if (anotherStudent != null)
                    return "Username is not available!";
                student.setUsername(account.username);
            }
            if (account.old_password != null) {
                if (!student.getPassword().equals(account.old_password))
                    return "Wrong password!";
                if (account.new_password.length() < 6)
                    return "Password must have more than 6 characters!";
                if (!account.new_password.equals(account.retype_password))
                    return "Passwords are not matched!";
                student.setPassword(account.new_password);
            }
            ss.merge(student);
            trans.commit();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return "Update successful!";
    }
}
