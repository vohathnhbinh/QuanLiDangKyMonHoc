package guis;

import daos.CourseDao;
import daos.RegSessionDao;
import daos.StudentDao;
import models.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class HomeStudent extends JFrame{
    private JPanel mainPanel;
    private JButton accountSettingButton;
    private JButton LOGOUTButton;
    private JLabel welcome;
    private JPanel toolbarPanel;
    private JPanel workPanel;
    private JScrollPane myCoursesPane;
    private JScrollPane coursesPane;
    private JTable myCoursesTable;
    private JTable coursesTable;
    private JLabel myCoursesLabel;
    private JLabel isRegisterLabel;
    private RegSession currentSession;

    private courseTableModel myCoursesModel;
    private courseTableModel coursesModel;
    private boolean isOutOfTime = true;

    public HomeStudent(final Student user) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manage Courses");
        this.setSize(1024, 768);

        final JDialog dialog = new AccountSetting(user);

        Action joinCourse = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Set<Student_Course> student_courses = user.getCourses();
                if (student_courses.size() >= 8) {
                    JOptionPane.showMessageDialog(getFrame(), "Không thể đăng ký quá 8 học phần!");
                    return;
                }
                int modelRow = Integer.valueOf(e.getActionCommand());
                List<Course> courses = coursesModel.getCourses();
                Course course = courses.get(modelRow);
                for (Student_Course myStudentCourse : student_courses) {
                    if (myStudentCourse.getCourse().getDate_of_week() == course.getDate_of_week()) {
                        if (myStudentCourse.getCourse().getShift() == course.getShift()) {
                            JOptionPane.showMessageDialog(getFrame(), "Không thể đăng ký trùng giờ học!");
                            return;
                        }
                    }
                }

                int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn tham gia?", "Xác nhận đăng ký", JOptionPane.YES_NO_OPTION);

                if (input == 0) {
                    JOptionPane.showMessageDialog(getFrame(), StudentDao.interactCourse(user, course, true));
                    myCoursesModel.add(course);
                    myCoursesModel.changeData();
                    coursesModel.remove(course);
                }
            }
        };

        Action quitCourse = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                List<Course> courses = myCoursesModel.getCourses();
                Course course = courses.get(modelRow);

                int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn thoát học phần?", "Xác nhận hủy đăng ký", JOptionPane.YES_NO_OPTION);

                if (input == 0) {
                    JOptionPane.showMessageDialog(getFrame(), StudentDao.interactCourse(user, course, false));
                    myCoursesModel.remove(course);
                    coursesModel.add(course);
                    coursesModel.changeData();
                }
            }
        };

        coursesPane.setVisible(false);
        myCoursesPane.setVisible(true);

        Date todayDate = new Date();
        List<RegSession> regSessions = RegSessionDao.getAll();
        for (RegSession regSession : regSessions) {
            if (todayDate.after(regSession.getBegin_date()) && todayDate.before(regSession.getExpired_date())) {
                currentSession = regSession;
                coursesPane.setVisible(true);
                isRegisterLabel.setText("");
                isOutOfTime = false;
                break;
            }
        }
        Set<Student_Course> theirCourses = user.getCourses();
        List<Course> studentCourses = new ArrayList<>();
        for (Student_Course studentCourse : theirCourses) {
            studentCourses.add(studentCourse.getCourse());
        }
        myCoursesModel = new courseTableModel(studentCourses);
        myCoursesModel.setMine(true);
        myCoursesModel.setOutOfTime(isOutOfTime);
        myCoursesTable.setModel(myCoursesModel);
        if (currentSession != null) {
            ButtonColumn buttonColumn1 = new ButtonColumn(myCoursesTable, quitCourse, 7);

            List<Course> theseCourses = CourseDao.getAll();
            List<Course> courses = new ArrayList<>();
            for (Course thisCourse: theseCourses) {
                if (currentSession.getSemester().equals(thisCourse.getSemester()))
                    courses.add(thisCourse);
            }
            courses.removeAll(studentCourses);
            coursesModel = new courseTableModel(courses);
            coursesModel.setMine(false);
            coursesTable.setModel(coursesModel);
            ButtonColumn buttonColumn2 = new ButtonColumn(coursesTable, joinCourse, 7);
        }

        welcome.setText(welcome.getText() + user.getFullname());
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new LogIn();
                frame.setVisible(true);
                getFrame().dispose();
            }
        });

        accountSettingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setModal(true);
                dialog.setVisible(true);
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                getFrame().revalidate();
                getFrame().repaint();
            }
        });
    }
    public JFrame getFrame() {
        return this;
    }

    class courseTableModel extends AbstractTableModel {
        private java.util.List<Course> courses;
        private boolean isMine;
        private boolean isOutOfTime;

        public courseTableModel() {
            courses = CourseDao.getAll();
        }

        public courseTableModel(java.util.List<Course> courses) {
            this.courses = courses;
        }

        public void setCourses(java.util.List<Course> courses) {
            this.courses = courses;
        }

        public void setOutOfTime(boolean isOutOfTime) { this.isOutOfTime = isOutOfTime;}

        public List<Course> getCourses() {
            return courses;
        }

        public void setMine(boolean mine) {
            isMine = mine;
        }

        @Override
        public int getRowCount() {
            return courses.size();
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Mã môn";
                case 1: return "Tên môn";
                case 2: return "Số tín chỉ";
                case 3: return "Giáo viên";
                case 4: return "Phòng học";
                case 5: return "Thời gian";
                case 6: return "Slot hiện tại";
                case 7: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int row, int col) {
            final Course course = courses.get(row);
            Subject mySubject = course.getSubject();
            switch (col) {
                case 0: return mySubject.getSubject_number();
                case 1: return mySubject.getSubject_name();
                case 2: return mySubject.getCredit_amount();
                case 3: return course.getTeacher().getTeacher_name();
                case 4: return course.getClassroom();
                case 5: return course.getDate_of_week().getDate() + " * " + course.getShift().getShift_time();
                case 6: return course.getStudents().size() + "/" + course.getMax_slot();
                case 7: return !isOutOfTime ? (isMine ? "QUIT" : "JOIN") : "";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            final Course course = courses.get(row);
            int currentSize = course.getStudents().size();
            int maxSize = course.getMax_slot();
            switch (col) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return false;
                case 7:
                    return currentSize < maxSize && !isOutOfTime;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Course course) {
            int row = courses.indexOf(course);
//            CourseDao.add(course);
            courses.add(course);
        }

        public void remove(Course course) {
            if (courses.contains(course)) {
                int row = courses.indexOf(course);
                courses.remove(row);
                fireTableRowsDeleted(row, row);
//                CourseDao.remove(course);
            }
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        myCoursesTable = new JTable();
        myCoursesPane = new JScrollPane(myCoursesTable);
        myCoursesPane.setPreferredSize(new Dimension(1024, 300));
        myCoursesTable.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        myCoursesTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 16));
        myCoursesTable.setFillsViewportHeight(true);

        coursesTable = new JTable();
        coursesPane = new JScrollPane(coursesTable);
        coursesPane.setPreferredSize(new Dimension(1024, 400));
        coursesTable.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        coursesTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 16));
        coursesTable.setFillsViewportHeight(true);
    }
}
