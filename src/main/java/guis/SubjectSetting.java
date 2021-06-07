package guis;

import daos.SubjectDao;
import models.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SubjectSetting extends JDialog {

    private JPanel mainPanel;
    private JTextField subjectTextField;
    private JTextField creditTextField;
    private JButton editSubjectButton;
    private JButton editCreditButton;
    private JTextField editSubjectTF;
    private JButton saveCreditButton;
    private JTextField editCreditTF;
    private JButton saveCreditBButton;
    private JLabel subjectLabel;
    private JLabel creditLabel;

    public SubjectSetting(final Subject subject) {
        this.setTitle("Subject Setting");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(800,600);

        subjectTextField.setText(subject.getSubject_name());
        creditTextField.setText(Integer.toString(subject.getCredit_amount()));
        subjectTextField.setEditable(false);
        creditTextField.setEditable(false);

        editSubjectTF.setVisible(false);
        editCreditTF.setVisible(false);
        saveCreditButton.setVisible(false);
        saveCreditBButton.setVisible(false);

        editSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editSubjectTF.isVisible()) {
                    editSubjectTF.setVisible(false);
                    saveCreditButton.setVisible(false);
                } else {
                    editSubjectTF.setVisible(true);
                    saveCreditButton.setVisible(true);
                }
            }
        });

        saveCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changedSubject = editSubjectTF.getText();
                if (changedSubject.length() == 0) {
                    JOptionPane.showMessageDialog(getDialog(), "Tên môn học trống!");
                    return;
                }
                List<Subject> subjects = SubjectDao.getAll();
                for (Subject thisSubject : subjects) {
                    if (changedSubject.equals(thisSubject.getSubject_name())) {
                        JOptionPane.showMessageDialog(getDialog(), "Tên môn học đã tồn tại!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(getDialog(), SubjectDao.changeInfo(subject, changedSubject, 0));
                subjectTextField.setText(subject.getSubject_name());
            }
        });
        editCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editCreditTF.isVisible()) {
                    editCreditTF.setVisible(false);
                    saveCreditBButton.setVisible(false);
                } else {
                    editCreditTF.setVisible(true);
                    saveCreditBButton.setVisible(true);
                }
            }
        });
        saveCreditBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changedCredit = editCreditTF.getText();
                if (changedCredit.length() == 0) {
                    JOptionPane.showMessageDialog(getDialog(), "Số tín chỉ trống!");
                    return;
                }
                JOptionPane.showMessageDialog(getDialog(), SubjectDao.changeInfo(subject, null, SubjectDao.myParseInt(changedCredit)));
                creditTextField.setText(Integer.toString(subject.getCredit_amount()));
            }
        });
    }

    public JDialog getDialog() {
        return this;
    }
}
