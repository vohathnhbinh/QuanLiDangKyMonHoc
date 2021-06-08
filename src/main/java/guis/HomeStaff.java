package guis;

import additionals.Gender;
import additionals.Role;
import daos.*;
import models.*;
import models.Class;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeStaff extends JFrame {
    private JPanel mainPanel;
    private JButton accountSettingButton;
    private JButton LOGOUTButton;
    private JLabel welcome;
    private JScrollPane staffPane;
    private JTable staffTable;
    private JTabbedPane tabbedPane1;
    private JPanel workPanel;
    private JPanel toolbarPanel;
    private JDialog dialog;

    // STAFF
    private JPanel funkyshitStaffPanel;
    private JTextField staffSearchField;
    private JButton staffSearchButton;
    private JTextField addStaffTextField;
    private JButton addStaffButton;
    private JButton resetStaffButton;
    private JPanel STAFFPanel;
    private JDialog dialogAlt;
    private staffTableModel staffModel;

    Action updateStaff = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Staff> staffs = staffModel.getStaffs();
            Staff staff = staffs.get(modelRow);

            dialogAlt = new AccountSettingAlt(staff);
            dialogAlt.setModal(true);
            dialogAlt.setVisible(true);
            staffModel.update(staff);
        }
    };

    Action deleteStaff = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Staff> staffs = staffModel.getStaffs();
            Staff staff = staffs.get(modelRow);

            int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                staffModel.remove(staff);
            }
        }
    };

    // SUBJECT
    private JPanel SUBJECTPanel;
    private JPanel funkyshitSubjectPanel;
    private JScrollPane subjectPane;
    private JTable subjectTable;
    private JTextField searchSubjectField;
    private JButton searchSubjectButton;
    private JButton resetSubjectButton;
    private JButton addSubjectButton;
    private JTextField subjectNameField;
    private JTextField creditField;
    private JDialog dialogSubject;
    private subjectTableModel subjectModel;

    Action updateSubject = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Subject> subjects = subjectModel.getSubjects();
            Subject subject = subjects.get(modelRow);

            dialogSubject = new SubjectSetting(subject);
            dialogSubject.setModal(true);
            dialogSubject.setVisible(true);
            subjectModel.update(subject);
        }
    };

    Action deleteSubject = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Subject> subjects = subjectModel.getSubjects();
            Subject subject = subjects.get(modelRow);

            int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                subjectModel.remove(subject);
            }
        }
    };

    // SEMESTER
    private JFormattedTextField startDate;
    private JFormattedTextField endDate;
    private JButton addSemesterButton;
    private JComboBox semesterCombobox;
    private JComboBox yearCombobox;
    private JPanel funkyshitSemesterPanel;
    private JScrollPane semesterPane;
    private JPanel SEMESTERPanel;
    private JTable semesterTable;
    private JLabel currentSemesterLabel;
    private semesterTableModel semesterModel;
    private Semester currentSemester = null;

    Action deleteSemester = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Semester> semesters = semesterModel.getSemesters();
            Semester semester = semesters.get(modelRow);

            int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                semesterModel.remove(semester);
                if (currentSemester.equals(semester))
                    currentSemesterLabel.setText("Học kì hiện tại");
            }
        }
    };

    Action chooseSemester = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Semester> semesters = semesterModel.getSemesters();
            Semester semester = semesters.get(modelRow);

            currentSemester = semester;
            currentSemesterLabel.setText(semester.getName() + " * " + semester.getSchool_year());
        }
    };

    // CLASS
    private JTextField classTextField;
    private JButton addClassButton;
    private JPanel funkyshitClassPanel;
    private JPanel CLASSPanel;
    private JScrollPane classPane;
    private JTable classTable;
    private classTableModel classModel;

    Action deleteClass = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Class> classes = classModel.getClasses();
            Class myClass = classes.get(modelRow);

            int input = JOptionPane.showConfirmDialog(getFrame(), "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                classModel.remove(myClass);
            }
        }
    };

    // STUDENT
    private JPanel STUDENTPanel;
    private JTextField searchStudentTextField;
    private JButton searchStudentButton;
    private JButton resetStudentButton;
    private JButton addStudentButton;
    private JComboBox genderCombobox;
    private JTextField studentNameTextField;
    private JComboBox classCombobox;
    private JScrollPane studentPane;
    private JTable studentTable;
    private JPanel funkyshitStudentPanel;
    private JDialog dialogStudent;
    private studentTableModel studentModel;

    Action updateStudent = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = Integer.valueOf(e.getActionCommand());
            List<Student> students = studentModel.getStudents();
            Student student = students.get(modelRow);

            dialogStudent = new AccountSettingAlt(student);
            dialogStudent.setModal(true);
            dialogStudent.setVisible(true);
            studentModel.update(student);
        }
    };

    public HomeStaff(final Staff user) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manage Courses");
        this.setSize(1024, 768);
        if (currentSemester != null)
            currentSemesterLabel.setText(currentSemester.getName() + " * " + currentSemester.getSchool_year());

        dialog = new AccountSetting(user);

        // STAFF
        staffModel = new staffTableModel();
        staffModel.setUser(user);
        staffTable.setModel(staffModel);
        ButtonColumn buttonColumn1 = new ButtonColumn(staffTable, updateStaff, 2);
        ButtonColumn buttonColumn2 = new ButtonColumn(staffTable, deleteStaff, 3);

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

        // STAFF
        staffSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = staffSearchField.getText();
                List<Staff> staffs = StaffDao.searchStaff(searchInfo, searchInfo);
                if (staffs.isEmpty())
                    JOptionPane.showMessageDialog(getFrame(), "Không tìm thấy giáo vụ!");
                staffModel.setStaffs(staffs);
                staffModel.changeData();
            }
        });
        addStaffTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (addStaffTextField.getText().isEmpty()) {
                    addStaffTextField.setText("Họ tên");
                    addStaffTextField.setBackground(Color.WHITE);
                }
            }
        });
        addStaffTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (addStaffTextField.getText().equals("Họ tên")) {
                    addStaffTextField.setText("");
                    addStaffTextField.setBackground(Color.WHITE);
                }
            }
        });
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullname = addStaffTextField.getText();
                Staff staff = new Staff();
                staff.setRole(Role.STAFF);
                staff.setFullname(fullname);
                staffModel.add(staff);
                JOptionPane.showMessageDialog(getFrame(), "Thêm thành công " + fullname);
            }
        });
        resetStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Staff> staffs = StaffDao.getAll();
                staffModel.setStaffs(staffs);
                staffModel.changeData();
            }
        });
        staffSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (staffSearchField.getText().isEmpty()) {
                    staffSearchField.setText("Tìm theo mã số hoặc họ tên giáo vụ");
                }
            }
        });
        staffSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (staffSearchField.getText().equals("Tìm theo mã số hoặc họ tên giáo vụ")) {
                    staffSearchField.setText("");
                }
            }
        });

        // SUBJECT
        searchSubjectField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (searchSubjectField.getText().isEmpty()) {
                    searchSubjectField.setText("Tìm theo mã, tên và tín chỉ học phần");
                    searchSubjectField.setBackground(Color.WHITE);
                }
            }
        });
        searchSubjectField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (searchSubjectField.getText().equals("Tìm theo mã, tên và tín chỉ học phần")) {
                    searchSubjectField.setText("");
                    searchSubjectField.setBackground(Color.WHITE);
                }
            }
        });
        subjectNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (subjectNameField.getText().isEmpty()) {
                    subjectNameField.setText("Tên môn học");
                    subjectNameField.setBackground(Color.WHITE);
                }
            }
        });
        subjectNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (subjectNameField.getText().equals("Tên môn học")) {
                    subjectNameField.setText("");
                    subjectNameField.setBackground(Color.WHITE);
                }
            }
        });
        creditField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (creditField.getText().isEmpty()) {
                    creditField.setText("Số tín chỉ");
                    creditField.setBackground(Color.WHITE);
                }
            }
        });
        creditField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (creditField.getText().equals("Số tín chỉ")) {
                    creditField.setText("");
                    creditField.setBackground(Color.WHITE);
                }
            }
        });
        searchSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = searchSubjectField.getText();
                List<Subject> subjects = SubjectDao.searchSubject(searchInfo, searchInfo, searchInfo);
                if (subjects.isEmpty())
                    JOptionPane.showMessageDialog(getFrame(), "Không tìm thấy môn học!");
                subjectModel.setSubjects(subjects);
                subjectModel.changeData();
            }
        });
        addSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subject_name = subjectNameField.getText();
                String credit = creditField.getText();
                if (subject_name.length() == 0) {
                    JOptionPane.showMessageDialog(getFrame(), "Tên môn học trống!");
                    return;
                }
                List<Subject> subjects = SubjectDao.getAll();
                for (Subject thisSubject : subjects) {
                    if (subject_name.equals(thisSubject.getSubject_name())) {
                        JOptionPane.showMessageDialog(getFrame(), "Tên môn học đã tồn tại!");
                        return;
                    }
                }
                if (subject_name.length() < 10) {
                    JOptionPane.showMessageDialog(getFrame(), "Tên môn học phải có trên 10 kí tự!");
                    return;
                }
                if (credit.length() == 0) {
                    JOptionPane.showMessageDialog(getFrame(), "Số tín chỉ trống!");
                    return;
                }
                int creditNum = SubjectDao.myParseInt(credit);
                if (creditNum < 2 || creditNum > 10) {
                    JOptionPane.showMessageDialog(getFrame(), "Số tín chỉ không hợp lệ!");
                    return;
                }
                Subject subject = new Subject();
                subject.setSubject_name(subject_name);
                subject.setCredit_amount(creditNum);
                subjectModel.add(subject);
                subjectModel.changeData();
                JOptionPane.showMessageDialog(getFrame(), "Thêm thành công " + subject_name);
            }
        });
        resetSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Subject> subjects = SubjectDao.getAll();
                subjectModel.setSubjects(subjects);
                subjectModel.changeData();
            }
        });
        SUBJECTPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (subjectModel == null) {
                    subjectModel = new subjectTableModel();
                    subjectTable.setModel(subjectModel);
                    ButtonColumn buttonColumn3 = new ButtonColumn(subjectTable, updateSubject, 3);
                    ButtonColumn buttonColumn4 = new ButtonColumn(subjectTable, deleteSubject, 4);
                    subjectTable.getColumnModel().getColumn(1).setMinWidth(300);
                }
            }
        });

        // SEMESTER
        SEMESTERPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (semesterModel == null) {
                    semesterModel = new semesterTableModel();
                    semesterTable.setModel(semesterModel);
                    ButtonColumn buttonColumn5 = new ButtonColumn(semesterTable, chooseSemester, 4);
                    ButtonColumn buttonColumn6 = new ButtonColumn(semesterTable, deleteSemester, 5);

                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    String item1 = Integer.toString(currentYear - 1) + '-' + Integer.toString(currentYear);
                    String item2 = Integer.toString(currentYear) + '-' + Integer.toString(currentYear + 1);
                    String item3 = Integer.toString(currentYear + 1) + '-' + Integer.toString(currentYear + 2);
                    yearCombobox.addItem("Năm học");
                    yearCombobox.addItem(item1);
                    yearCombobox.addItem(item2);
                    yearCombobox.addItem(item3);
                }
            }
        });
        startDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (startDate.getText().isEmpty()) {
                    startDate.setText("Ngày bắt đầu (dd-mm-yyyy)");
                }
            }
        });
        startDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (startDate.getText().equals("Ngày bắt đầu (dd-mm-yyyy)")) {
                    startDate.setText("");
                }
            }
        });
        endDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (startDate.getText().isEmpty()) {
                    startDate.setText("Ngày kết thúc (dd-mm-yyyy)");
                }
            }
        });
        endDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (startDate.getText().equals("Ngày kết thúc (dd-mm-yyyy)")) {
                    startDate.setText("");
                }
            }
        });
        addSemesterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat oldFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String oldStartDate = (String) startDate.getText();
                Date newStartDate = null;
                String oldEndDate = (String) endDate.getText();
                Date newEndDate = null;
                try {
                    Date startD = oldFormat.parse(oldStartDate);
                    System.out.println(oldStartDate);
                    System.out.println(startD);
                    Date endD = oldFormat.parse(oldEndDate);
                    newStartDate = newFormat.parse(newFormat.format(startD));
                    newEndDate = newFormat.parse(newFormat.format(endD));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                String semester_name = (String) semesterCombobox.getSelectedItem();
                String school_year = (String) yearCombobox.getSelectedItem();
                if (semester_name.equals("Học kì")) {
                    JOptionPane.showMessageDialog(getFrame(), "Hãy chọn học kì!");
                    return;
                }
                if (school_year.equals("Năm học")) {
                    JOptionPane.showMessageDialog(getFrame(), "Hãy chọn năm học!");
                    return;
                }
                Semester semester = new Semester();
                semester.setName(semester_name);
                semester.setSchool_year(school_year);
                semester.setStart_date(newStartDate);
                semester.setEnd_date(newEndDate);
                semesterModel.add(semester);
                semesterModel.changeData();
            }
        });

        // CLASS
        CLASSPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (classModel == null) {
                    classModel = new classTableModel();
                    classTable.setModel(classModel);
                    ButtonColumn buttonColumn7 = new ButtonColumn(classTable, deleteClass, 4);
                }
            }
        });
        addClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String class_name = classTextField.getText();
                if (class_name.length() == 0) {
                    JOptionPane.showMessageDialog(getFrame(), "Tên lớp học trống!");
                    return;
                }
                Class myClass = new Class();
                myClass.setClass_name(class_name);
                classModel.add(myClass);
                classModel.changeData();
            }
        });
        classTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (classTextField.getText().isEmpty()) {
                    classTextField.setText("Tên lớp học");
                }
            }
        });
        classTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (classTextField.getText().equals("Tên lớp học")) {
                    classTextField.setText("");
                }
            }
        });

        // STUDENT
        STUDENTPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (studentModel == null) {
                    studentModel = new studentTableModel();
                    studentTable.setModel(studentModel);
                    ButtonColumn buttonColumn8 = new ButtonColumn(studentTable, updateStudent, 4);

                    classCombobox.addItem("Lớp học");
                    List<Class> classes = ClassDao.getAll();
                    for (Class thisClass : classes) {
                        classCombobox.addItem(thisClass.getClass_name());
                    }
                }
            }
        });
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
        studentNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (studentNameTextField.getText().isEmpty()) {
                    studentNameTextField.setText("Họ tên");
                }
            }
        });
        studentNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (studentNameTextField.getText().equals("Họ tên")) {
                    studentNameTextField.setText("");
                }
            }
        });
        searchStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = searchStudentTextField.getText();
                List<Student> students = StudentDao.searchStudent(searchInfo, searchInfo);
                if (students.isEmpty())
                    JOptionPane.showMessageDialog(getFrame(), "Không tìm thấy sinh viên!");
                studentModel.setStudents(students);
                studentModel.changeData();
            }
        });
        resetStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Student> students = StudentDao.getAll();
                studentModel.setStudents(students);
                studentModel.changeData();
            }
        });
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullname = studentNameTextField.getText();
                String gender = (String)genderCombobox.getSelectedItem();
                String class_name = (String)classCombobox.getSelectedItem();
                Class myClass = ClassDao.findOne(class_name, "class_name");


                if (gender.equals("Giới tính")) {
                    JOptionPane.showMessageDialog(getFrame(), "Hãy chọn giới tính!");
                    return;
                }
                if (class_name.equals("Lớp học")) {
                    JOptionPane.showMessageDialog(getFrame(), "Hãy chọn lớp học!");
                    return;
                }
                Gender realGender = gender.equals("NAM") ? Gender.MALE : Gender.FEMALE;
                    Student student = new Student(fullname, myClass, realGender);

                studentModel.add(student);
                studentModel.changeData();
                classModel.setClasses(ClassDao.getAll());
                classModel.changeData();
            }
        });
    }
    public JFrame getFrame() {
        return this;
    }

    class staffTableModel extends AbstractTableModel {
        private List<Staff> staffs;
        private Staff user;

        public staffTableModel() {
            staffs = StaffDao.getAll();
        }

        public staffTableModel(List<Staff> staffs) {
            this.staffs = staffs;
        }

        public void setStaffs(List<Staff> staffs) {
            this.staffs = staffs;
        }

        public List<Staff> getStaffs() {
            return staffs;
        }

        public void setUser(Staff user) {
            this.user = user;
        }

        @Override
        public int getRowCount() {
            return staffs.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Mã số giáo vụ";
                case 1: return "Họ tên";
                case 2:
                case 3: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final Staff staff = staffs.get(rowIndex);
            switch (columnIndex) {
                case 0: return staff.getStaff_number();
                case 1: return staff.getFullname();
                case 2: return "EDIT";
                case 3: return "DELETE";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            switch (col) {
                case 0:
                case 1:
                    return false;
                case 2:
                case 3:
                    return true;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Staff staff) {
            staffs.add(staff);
            int row = staffs.indexOf(staff);
            StaffDao.add(staff);
            fireTableRowsInserted(row, row);
        }

        public void update(final Staff staff) {
            dialogAlt.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    int row = staffs.indexOf(staff);
                    if (row >= 0) {
                        staffs.set(row, staff);
                        fireTableRowsUpdated(row, row);
                    }
                }
            });
        }

        public void remove(Staff staff) {
            if (user.equals(staff)) {
                JOptionPane.showMessageDialog(getFrame(), "Không thể xóa bản thân");
            } else if (staffs.contains(staff)) {
                int row = staffs.indexOf(staff);
                staffs.remove(row);
                fireTableRowsDeleted(row, row);
                StaffDao.remove(staff);
            }
        }
    }

    class subjectTableModel extends AbstractTableModel {
        private List<Subject> subjects;

        public subjectTableModel() {
            subjects = SubjectDao.getAll();
        }

        public subjectTableModel(List<Subject> subjects) {
            this.subjects = subjects;
        }

        public void setSubjects(List<Subject> subjects) {
            this.subjects = subjects;
        }

        public List<Subject> getSubjects() {
            return subjects;
        }

        @Override
        public int getRowCount() {
            return subjects.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Mã số môn học";
                case 1: return "Tên";
                case 2: return "Số tín chỉ";
                case 3:
                case 4: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final Subject subject = subjects.get(rowIndex);
            switch (columnIndex) {
                case 0: return subject.getSubject_number();
                case 1: return subject.getSubject_name();
                case 2: return subject.getCredit_amount();
                case 3: return "EDIT";
                case 4: return "DELETE";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            switch (col) {
                case 0:
                case 1:
                case 2:
                    return false;
                case 3:
                case 4:
                    return true;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Subject subject) {
            int row = subjects.indexOf(subject);
            SubjectDao.add(subject);
            subjects.add(subject);
        }

        public void update(final Subject subject) {
            dialogSubject.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    int row = subjects.indexOf(subject);
                    if (row >= 0) {
                        subjects.set(row, subject);
                        fireTableRowsUpdated(row, row);
                    }
                }
            });
        }

        public void remove(Subject subject) {
            if (subjects.contains(subject)) {
                int row = subjects.indexOf(subject);
                subjects.remove(row);
                fireTableRowsDeleted(row, row);
                SubjectDao.remove(subject);
            }
        }
    }

    class semesterTableModel extends AbstractTableModel {
        private List<Semester> semesters;

        public semesterTableModel() {
            semesters = SemesterDao.getAll();
        }

        public semesterTableModel(List<Semester> semesters) {
            this.semesters = semesters;
        }

        public void setSemesters(List<Semester> semesters) {
            this.semesters = semesters;
        }

        public List<Semester> getSemesters() {
            return semesters;
        }

        @Override
        public int getRowCount() {
            return semesters.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Học kì";
                case 1: return "Năm học";
                case 2: return "Ngày bắt đầu";
                case 3: return "Ngày kết thúc";
                case 4:
                case 5: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            final Semester semester = semesters.get(rowIndex);
            switch (columnIndex) {
                case 0: return semester.getName();
                case 1: return semester.getSchool_year();
                case 2: return dateFormat.format(semester.getStart_date());
                case 3: return dateFormat.format(semester.getEnd_date());
                case 4: return "CHOOSE";
                case 5: return "DELETE";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            switch (col) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return false;
                case 4:
                case 5:
                    return true;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Semester semester) {
            int row = semesters.indexOf(semester);
            SemesterDao.add(semester);
            semesters.add(semester);
        }

        public void remove(Semester semester) {
            if (semesters.contains(semester)) {
                int row = semesters.indexOf(semester);
                semesters.remove(row);
                fireTableRowsDeleted(row, row);
                SemesterDao.remove(semester);
            }
        }
    }

    class classTableModel extends AbstractTableModel {
        private List<Class> classes;

        public classTableModel() {
            classes = ClassDao.getAll();
        }

        public classTableModel(List<Class> classes) {
            this.classes = classes;
        }

        public void setClasses(List<Class> classes) {
            this.classes = classes;
        }

        public List<Class> getClasses() {
            return classes;
        }

        @Override
        public int getRowCount() {
            return classes.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Tên lớp";
                case 1: return "Tổng số sinh viên";
                case 2: return "Số sinh viên nam";
                case 3: return "Số sinh viên nữ";
                case 4: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final Class myClass = classes.get(rowIndex);
            switch (columnIndex) {
                case 0: return myClass.getClass_name();
                case 1: return myClass.getClass_size();
                case 2: return myClass.getMale_size();
                case 3: return myClass.getFemale_size();
                case 4: return "DELETE";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            switch (col) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return false;
                case 4:
                    return true;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Class myClass) {
            int row = classes.indexOf(myClass);
            ClassDao.add(myClass);
            classes.add(myClass);
        }

        public void remove(Class myClass) {
            if (classes.contains(myClass)) {
                int row = classes.indexOf(myClass);
                classes.remove(row);
                fireTableRowsDeleted(row, row);
                ClassDao.remove(myClass);
            }
        }
    }

    class studentTableModel extends AbstractTableModel {
        private List<Student> students;

        public studentTableModel() {
            students = StudentDao.getAll();
        }

        public studentTableModel(List<Student> students) {
            this.students = students;
        }

        public void setStudents(List<Student> students) {
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
        public String getColumnName(int column) {
            switch (column) {
                case 0: return "Mã số sinh viên";
                case 1: return "Họ tên";
                case 2: return "Giới tính";
                case 3: return "Lớp";
                case 4: return "";
            }
            return null;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final Student student = students.get(rowIndex);
            switch (columnIndex) {
                case 0: return student.getStudent_number();
                case 1: return student.getFullname();
                case 2: return student.getGender() == Gender.MALE ? "NAM" : "NỮ";
                case 3: return student.getMyClass().getClass_name();
                case 4: return "EDIT";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            switch (col) {
                case 0:
                case 1:
                    return false;
                case 2:
                case 3:
                    return true;
            }
            return false;
        }

        public void changeData() {
            fireTableDataChanged();
        }

        public void add(Student student) {
            students.add(student);
            int row = students.indexOf(student);
            StudentDao.add(student);
        }

        public void update(final Student student) {
            dialogStudent.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    int row = students.indexOf(student);
                    if (row >= 0) {
                        students.set(row, student);
                        fireTableRowsUpdated(row, row);
                    }
                }
            });
        }
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
        // STAFF
        staffTable = new JTable();
        staffPane = new JScrollPane(staffTable);
        staffPane.setPreferredSize(new Dimension(1024, 600));
        staffTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        staffTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        staffTable.setFillsViewportHeight(true);

        subjectTable = new JTable();
        subjectPane = new JScrollPane(subjectTable);
        subjectPane.setPreferredSize(new Dimension(1024, 600));
        subjectTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        subjectTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        subjectTable.setFillsViewportHeight(true);

        semesterTable = new JTable();
        semesterPane = new JScrollPane(semesterTable);
        semesterPane.setPreferredSize(new Dimension(1024, 600));
        semesterTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        semesterTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        semesterTable.setFillsViewportHeight(true);

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        startDate = new JFormattedTextField(format);
        endDate = new JFormattedTextField(format);

        classTable = new JTable();
        classPane = new JScrollPane(classTable);
        classPane.setPreferredSize(new Dimension(1024, 600));
        classTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        classTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        classTable.setFillsViewportHeight(true);

        studentTable = new JTable();
        studentPane = new JScrollPane(studentTable);
        studentPane.setPreferredSize(new Dimension(1024, 600));
        studentTable.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        studentTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        studentTable.setFillsViewportHeight(true);
    }
}
