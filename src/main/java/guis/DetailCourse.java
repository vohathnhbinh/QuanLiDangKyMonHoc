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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private JLabel studentLabel;
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
        List<Date> registerDates = new ArrayList<>();
        for (Student_Course thisStudentCourse : student_courses) {
            originalStudents.add(thisStudentCourse.getStudent());
            registerDates.add(thisStudentCourse.getcreate_on());
        }
        studentModel = new studentTableModel(originalStudents, registerDates);
        studentTable.setModel(studentModel);

        searchStudentTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (searchStudentTextField.getText().isEmpty()) {
                    searchStudentTextField.setText("T??m theo m?? s??? ho???c h??? t??n sinh vi??n");
                }
            }
        });
        searchStudentTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (searchStudentTextField.getText().equals("T??m theo m?? s??? ho???c h??? t??n sinh vi??n")) {
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
                List<Student> students = StudentDao.searchStudent(searchInfo, originalStudents);
                if (students.isEmpty())
                    JOptionPane.showMessageDialog(getDialog(), "Kh??ng t??m th???y sinh vi??n!");
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
        topPane.setPreferredSize(new Dimension(1280, 200));
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
                case 0: return "M?? m??n";
                case 1: return "T??n m??n";
                case 2: return "S??? t??n ch???";
                case 3: return "Gi??o vi??n";
                case 4: return "Ph??ng h???c";
                case 5: return "Th???i gian";
                case 6: return "Slot hi???n t???i";
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
        private java.util.List<Date> registerDates;

        public studentTableModel() {
            students = StudentDao.getAll();
        }

        public studentTableModel(java.util.List<Student> students, java.util.List<Date> registerDates) {
            this.students = students;
            this.registerDates = registerDates;
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
            return 5;
        }

        @Override
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "M?? s??? sinh vi??n";
                case 1: return "H??? t??n";
                case 2: return "Gi???i t??nh";
                case 3: return "L???p";
                case 4: return "Th??i gian ????ng k??";
            }
            return null;
        }

        @Override
        public Object getValueAt(int row, int col) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            final Student student = students.get(row);
            final Date registerDate = registerDates.get(row);
            switch (col) {
                case 0: return student.getStudent_number();
                case 1: return student.getFullname();
                case 2: return student.getGender() == Gender.MALE ? "NAM" : "N???";
                case 3: return student.getMyClass().getClass_name();
                case 4: return dateFormat.format(registerDate);
            }
            return null;
        }

        public void changeData() {
            fireTableDataChanged();
        }
    }
}
