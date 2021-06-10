package guis;

import additionals.Gender;
import daos.CourseDao;
import models.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class DetailStudent extends JDialog {
    private JPanel mainPanel;
    private JTable currentStudentTable;
    private JScrollPane topPane;
    private JPanel COURSEPanel;
    private JPanel funkyshitCoursePanel;
    private JTextField searchCourseTextField;
    private JButton searchCourseButton;
    private JButton resetCourseButton;
    private JScrollPane coursePane;
    private JTable courseTable;
    private JLabel courseLabel;
    private courseTableModel courseModel;
    private studentTableModel studentModel;
    private List<Course> originalCourses = new ArrayList<>();

    public DetailStudent(Student student, Semester currentSemester) {
        this.setTitle("List course");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(1024,768);

        if (currentSemester == null)
            JOptionPane.showMessageDialog(getDialog(), "Nhớ chọn học kì hiện tại!");
        else
            courseLabel.setText(courseLabel.getText() + currentSemester.getName() + " * " + currentSemester.getSchool_year());

        studentModel = new studentTableModel(student);
        currentStudentTable.setModel(studentModel);

        Set<Student_Course> student_courses = student.getCourses();
        List<Date> registerDates = new ArrayList<>();
        for (Student_Course thisStudentCourse : student_courses) {
            if (thisStudentCourse.getCourse().getSemester().equals(currentSemester)) {
                originalCourses.add(thisStudentCourse.getCourse());
                registerDates.add(thisStudentCourse.getcreate_on());
            }
        }

        courseModel = new courseTableModel(originalCourses, registerDates);
        courseTable.setModel(courseModel);

        searchCourseTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (searchCourseTextField.getText().isEmpty()) {
                    searchCourseTextField.setText("Tìm kiếm học phần");
                }
            }
        });
        searchCourseTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (searchCourseTextField.getText().equals("Tìm kiếm học phần")) {
                    searchCourseTextField.setText("");
                }
            }
        });
        resetCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courseModel.setCourses(originalCourses);
                courseModel.changeData();
            }
        });
        searchCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = searchCourseTextField.getText();
                List<Course> courses = CourseDao.searchCourse(searchInfo);
                if (courses.isEmpty())
                    JOptionPane.showMessageDialog(getDialog(), "Không tìm học phần!");
                courseModel.setCourses(courses);
                courseModel.changeData();
            }
        });
    }

    private JDialog getDialog() {
        return this;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        topPane = new JScrollPane(currentStudentTable);
        topPane.setPreferredSize(new Dimension(1280, 200));
        currentStudentTable = new JTable();
        currentStudentTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        currentStudentTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        currentStudentTable.setFillsViewportHeight(true);

        courseTable = new JTable();
        coursePane = new JScrollPane(courseTable);
        coursePane.setPreferredSize(new Dimension(1280, 600));
        courseTable.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        courseTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 16));
        courseTable.setFillsViewportHeight(true);
    }

    class courseTableModel extends AbstractTableModel {
        private List<Course> courses;
        private List<Date> registerDates;

        public courseTableModel(List<Course> courses, List<Date> registerDates) {
            this.courses = courses;
            this.registerDates = registerDates;
        }

        public void setCourses(List<Course> courses) {
            this.courses = courses;
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
                case 7: return "Thời gian đăng ký";
            }
            return null;
        }

        @Override
        public Object getValueAt(int row, int col) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            final Course course = courses.get(row);
            final Date registerDate = registerDates.get(row);
            Subject mySubject = course.getSubject();
            switch (col) {
                case 0: return mySubject.getSubject_number();
                case 1: return mySubject.getSubject_name();
                case 2: return mySubject.getCredit_amount();
                case 3: return course.getTeacher().getTeacher_name();
                case 4: return course.getClassroom();
                case 5: return course.getDate_of_week().getDate() + " * " + course.getShift().getShift_time();
                case 6: return course.getStudents().size() + "/" + course.getMax_slot();
                case 7: return dateFormat.format(registerDate);
            }
            return null;
        }

        public void changeData() {
            fireTableDataChanged();
        }

    }

    class studentTableModel extends AbstractTableModel {
        private Student student;

        public studentTableModel(Student student) {
            this.student = student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Mã số sinh viên";
                case 1: return "Họ tên";
                case 2: return "Giới tính";
                case 3: return "Lớp";
            }
            return null;
        }

        @Override
        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0: return student.getStudent_number();
                case 1: return student.getFullname();
                case 2: return student.getGender() == Gender.MALE ? "NAM" : "NỮ";
                case 3: return student.getMyClass().getClass_name();
            }
            return null;
        }
    }
}
