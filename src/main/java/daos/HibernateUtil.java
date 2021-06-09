package daos;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static void buildSessionFactory(String url, String username, String password) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        if (url != null)
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + url + "/managecourses?useUnicode=true&amp;characterEncoding=UTF-8");
        else
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/managecourses?useUnicode=true&amp;characterEncoding=UTF-8");
        if (username != null)
            configuration.setProperty("hibernate.connection.username", username);
        else
            configuration.setProperty("hibernate.connection.username", "root");
        if (password != null)
            configuration.setProperty("hibernate.connection.password", password);
        else
            configuration.setProperty("hibernate.connection.password", "carot");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        MetadataSources metadata = new MetadataSources(serviceRegistry);

//        <mapping class="models.User" />
//        <mapping class="models.Student" />
//        <mapping class="models.Staff" />
//        <mapping class="models.Subject" />
//        <mapping class="models.Semester" />
//        <mapping class="models.Class" />
//        <mapping class="models.Course" />
//        <mapping class="models.RegSession" />
//        <mapping class="models.StudentCourseKey" />
//        <mapping class="models.Student_Course" />
//        <mapping class="models.Teacher" />

        metadata.addAnnotatedClass(models.User.class);
        metadata.addAnnotatedClass(models.Student.class);
        metadata.addAnnotatedClass(models.Staff.class);
        metadata.addAnnotatedClass(models.Subject.class);
        metadata.addAnnotatedClass(models.Semester.class);
        metadata.addAnnotatedClass(models.Class.class);
        metadata.addAnnotatedClass(models.Course.class);
        metadata.addAnnotatedClass(models.RegSession.class);
        metadata.addAnnotatedClass(models.StudentCourseKey.class);
        metadata.addAnnotatedClass(models.Student_Course.class);
        metadata.addAnnotatedClass(models.Teacher.class);

        sessionFactory = metadata.buildMetadata().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
