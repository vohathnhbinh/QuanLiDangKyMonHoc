package guis;

import additionals.Role;
import daos.StaffDao;
import daos.SubjectDao;
import models.Staff;
import models.Subject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
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


    public HomeStaff(final Staff user) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manage Courses");
        this.setSize(1024, 768);

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
                    JOptionPane.showMessageDialog(getFrame(), "Không tìm môn học!");
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
    }
}
