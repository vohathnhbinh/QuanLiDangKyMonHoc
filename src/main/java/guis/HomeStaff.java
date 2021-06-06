package guis;

import additionals.Role;
import daos.StaffDao;
import models.Staff;
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
    private JPanel funkyshitStaffPanel;
    private JTextField staffSearchField;
    private JButton staffSearchButton;
    private JTextField addTextField;
    private JButton addButton;
    private JButton resetButton;

    private JDialog dialog;
    private JDialog dialogAlt;
    staffTableModel staffModel;

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

            int input = JOptionPane.showConfirmDialog(getFrame(), "Do you really want to delete this?", "Delete confirm", JOptionPane.YES_NO_OPTION);

            if (input == 0) {
                staffModel.remove(staff);
            }
        }
    };

    public HomeStaff(final Staff user) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Manage Courses");
        this.setSize(800, 600);

        dialog = new AccountSetting(user);

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

        staffSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchInfo = staffSearchField.getText();
                List<Staff> staffs = StaffDao.searchStaff(searchInfo, searchInfo);
                if (staffs.isEmpty())
                    JOptionPane.showMessageDialog(getFrame(), "Staff not found!");
                staffModel.setStaffs(staffs);
                staffModel.changeData();
            }
        });
        addTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (addTextField.getText().isEmpty()) {
                    addTextField.setText("Fullname");
                    addTextField.setBackground(Color.WHITE);
                }
            }
        });
        addTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (addTextField.getText().equals("Fullname")) {
                    addTextField.setText("");
                    addTextField.setBackground(Color.WHITE);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullname = addTextField.getText();
                Staff staff = new Staff();
                staff.setRole(Role.STAFF);
                staff.setFullname(fullname);
                staffModel.add(staff);
                JOptionPane.showMessageDialog(getFrame(), "Succesfully add " + fullname);
            }
        });
        resetButton.addActionListener(new ActionListener() {
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
                    staffSearchField.setText("Search by staff number or fullname");
                }
            }
        });
        staffSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (staffSearchField.getText().equals("Search by staff number or fullname")) {
                    staffSearchField.setText("");
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
                case 0: return "Staff number";
                case 1: return "Fullname";
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
                JOptionPane.showMessageDialog(getFrame(), "Can't delete yourself");
            } else if (staffs.contains(staff)) {
                int row = staffs.indexOf(staff);
                staffs.remove(row);
                fireTableRowsDeleted(row, row);
                StaffDao.remove(staff);
            }
        }
    }



    public void createUIComponents() {
        // TODO: place custom component creation code here
        staffTable = new JTable();
        staffPane = new JScrollPane(staffTable);
        staffPane.setPreferredSize(new Dimension(800, 400));
        staffTable.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        staffTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 24));
        staffTable.setFillsViewportHeight(true);
    }
}
