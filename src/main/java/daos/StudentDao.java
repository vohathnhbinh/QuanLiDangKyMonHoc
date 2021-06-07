package daos;

import additionals.Account_Info;
import models.Class;
import models.Course;
import models.Student;
import models.Student_Course;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

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
            ss.close();
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
            Query query = null;
            if (field.substring(field.length() - 2).equals("id"))
                query = ss.createQuery(hql).setParameter(field, Integer.parseInt(cond));
            else
                query = ss.createQuery(hql).setParameter(field, cond);
            student = (Student) query.uniqueResult();
            trans.commit();
            ss.close();
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
            ss.close();
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
                    return "Username phải có trên 6 kí tự!";
                Student anotherStudent = findOne(account.username, "username");
                if (anotherStudent != null)
                    return "Username đã tồn tại!";
                student.setUsername(account.username);
            }
            if (account.old_password != null) {
                if (!student.getPassword().equals(account.old_password))
                    return "Sai mật khẩu!";
                if (account.new_password.length() < 6)
                    return "Mật khẩu phải có trên 6 kí tự!";
                if (!account.new_password.equals(account.retype_password))
                    return "Mật khẩu không trùng khớp nhau!";
                student.setPassword(account.new_password);
            }
            ss.merge(student);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            System.err.println(err);
        }
        return "Cập nhật thông tin thành công!";
    }

    public static String interactCourse(Student student, Course course, boolean isJoin) {
        Transaction trans = null;
        if (isJoin) {
            Set<Student_Course> courses = student.getCourses();
            for (Student_Course thisCourse : courses) {
                if (thisCourse.getCourse().getCourse_id() == course.getCourse_id())
                    return "Đã tham gia học phần!";
                if (thisCourse.getCourse().getDate_of_week().equals(course.getDate_of_week())) {
                    if (thisCourse.getCourse().getShift().equals(course.getShift()))
                        return "Kẹt thời gian!";
                }
            }
        }

        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            if (isJoin)
                student.addCourse(course);
            else
                student.removeCourse(course);
            ss.merge(student);
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        if (isJoin)
            return "Đăng ký học phần thành công";
        return "Hủy đăng ký học phần thành công";
    }

    public static List<Student> searchStudent(String student_number, String fullname) {
        Transaction trans = null;
        try {
            Session ss = (Session) HibernateUtil.getSessionFactory().openSession();
            trans = ss.beginTransaction();
            String hql = "FROM Student " +
                    "WHERE (student_number = :student_number) OR" +
                    "(LOWER(fullname) LIKE :exfullname)";
            Query query = ss.createQuery(hql);
            query.setParameter("student_number", student_number);
            query.setParameter("exfullname", '%' + fullname.toLowerCase() + '%');
            students = query.list();
            trans.commit();
            ss.close();
        } catch (HibernateError err) {
            trans.rollback();
            System.err.println(err);
        }
        return students;
    }

    public static Student logIn(String username, String password) {
        Student student = (Student) findOne(username, "username");
        if (student != null) {
            if (password.equals(student.getPassword()))
                return student;
            else
                return null;
        } else
            return null;
    }
}
