package guis;

import additionals.Gender;
import daos.StudentDao;
import models.Course;
import models.Student;
import models.Student_Course;
import models.Subject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DetailCourse extends JDialog {
    private JPanel mainPanel;
    private JTable currentCourseTable;
    private JScrollPane topPane;
    private JPanel STUDENTPanel;
    private JPanel funkyshitStudentPanel;
    private JTextField searchStudentTextField;
    private JButton searchStudentButton;
    private JButton resetStudentButton;
    private JScrollPane studentPane;
    private JTable studentTable;
    private courseTableModel courseModel;
    private studentTableModel studentModel;
    private List<Student> originalStudents = new ArrayList<>();

    public DetailCourse(Course course) {
        this.setTitle("List student");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(1024,768);

        courseModel = new courseTableModel(course);
        currentCourseTable.setModel(courseModel);

        Set<Student_Course> student_courses = course.getStudents();
        for (Student_Course thisStudentCourse : student_courses) {
            originalStudents.add(thisStudentCourse.getStudent());
        }
        studentModel = new studentTableModel(originalStudents);
        studentTable.setModel(studentModel);

        searchStudentTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (searchStudentTextField.getText().isEmpty()) {
                    searchStudentTextField.setText("Tìm theo mã số hoặc họ tên sinh viên");
                }
            }
        });
        searchStudentTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (searchStudentTextField.getText().equals("Tìm theo mã số hoặc họ tên sinh viên")) {
                    searchStudentTextField.setText("");
                }
            }
        });
        resetStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentModel.setStudents(originalStudents);
                studentModel.changeData();
            }
        });
        searchStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = searchStudentTextField.getText();
                List<Student> students = StudentDao.searchStudent(searchInfo, searchInfo);
                if (students.isEmpty())
                    JOptionPane.showMessageDialog(getDialog(), "Không tìm thấy sinh viên!");
                studentModel.setStudents(students);
                studentModel.changeData();
            }
        });
    }

    private JDialog getDialog() {
        return this;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        topPane = new JScrollPane(currentCourseTable);
        topPane.setPreferredSize(new Dimension(1280, 100));
        currentCourseTable = new JTable();
        currentCourseTable.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        currentCourseTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 16));
        currentCourseTable.setFillsViewportHeight(true);

        studentTable = new JTable();
        studentPane = new JScrollPane(studentTable);
        studentPane.setPreferredSize(new Dimension(1280, 600));
        studentTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        studentTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        studentTable.setFillsViewportHeight(true);
    }

    class courseTableModel extends AbstractTableModel {
        private Course course;

        public courseTableModel(Course course) {
            this.course = course;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 7;
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
            }
            return null;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Subject mySubject = course.getSubject();
            switch (col) {
                case 0: return mySubject.getSubject_number();
                case 1: return mySubject.getSubject_name();
                case 2: return mySubject.getCredit_amount();
                case 3: return course.getTeacher().getTeacher_name();
                case 4: return course.getClassroom();
                case 5: return course.getDate_of_week().getDate() + " * " + course.getShift().getShift_time();
                case 6: return course.getStudents().size() + "/" + course.getMax_slot();
            }
            return null;
        }
    }

    class studentTableModel extends AbstractTableModel {
        private java.util.List<Student> students;

        public studentTableModel() {
            students = StudentDao.getAll();
        }

        public studentTableModel(java.util.List<Student> students) {
            this.students = students;
        }

        public void setStudents(java.util.List<Student> students) {
            this.students = students;
        }

        public List<Student> getStudents() {
            return students;
        }

        @Override
        public int getRowCount() {
            return students.size();
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
            final Student student = students.get(row);
            switch (col) {
                case 0: return student.getStudent_number();
                case 1: return student.getFullname();
                case 2: return student.getGender() == Gender.MALE ? "NAM" : "NỮ";
                case 3: return student.getMyClass().getClass_name();
            }
            return null;
        }

        public void changeData() {
            fireTableDataChanged();
        }
    }
}
