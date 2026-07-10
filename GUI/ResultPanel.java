package GUI;
import Model.Student;
import Model.Subject;
import Model.StudentSubject;
import Service.GradeCalculate;
import Service.GradeManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class ResultPanel extends JPanel
{
    private GradeManager manager;
    private JComboBox<Student> studentCombo;
    private JComboBox<Subject> subjectCombo;
    private JTextField assignmentField;
    private JTextField testField;
    private JTextField examField;
    private DefaultTableModel tableModel;

    public ResultPanel(GradeManager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildInterface();
    }

    private void buildInterface()
    {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        studentCombo = new JComboBox<Student>();
        subjectCombo = new JComboBox<Subject>();
        assignmentField = new JTextField();
        testField = new JTextField();
        examField = new JTextField();

        formPanel.add(new JLabel("Student:"));
        formPanel.add(studentCombo);
        formPanel.add(new JLabel("Subject:"));
        formPanel.add(subjectCombo);
        formPanel.add(new JLabel("Assignment Mark:"));
        formPanel.add(assignmentField);
        formPanel.add(new JLabel("Test Mark:"));
        formPanel.add(testField);
        formPanel.add(new JLabel("Final Exam Mark:"));
        formPanel.add(examField);

        JButton addButton = new JButton("Add Result");
        JButton updateButton = new JButton("Update Result");
        JButton deleteButton = new JButton("Delete Result");
        JButton refreshButton = new JButton("Refresh Lists");

        addButton.addActionListener(event -> addResult());
        updateButton.addActionListener(event -> updateResult());
        deleteButton.addActionListener(event -> deleteResult());
        refreshButton.addActionListener(event -> refreshAll());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
            new String[] {
                "Student ID",
                "Student Name",
                "Subject",
                "Assignment",
                "Test",
                "Exam",
                "Overall",
                "Grade",
                "Status"
            },
            0
        )
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        refreshAll();
    }

    private void addResult()
    {
        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null)
        {
            showMessage("Add at least one student and subject first.");
            return;
        }

        try
        {
            double assignment = Double.parseDouble(
                assignmentField.getText().trim()
            );
            double test = Double.parseDouble(testField.getText().trim());
            double exam = Double.parseDouble(examField.getText().trim());

            if (!validMarks(assignment, test, exam))
            {
                showMessage("Marks must be between 0 and 100.");
                return;
            }

            boolean added = manager.addStudentSubject(
                new StudentSubject(student, subject, assignment, test, exam)
            );

            if (!added)
            {
                showMessage("This student already has a result for this subject.");
                return;
            }

            refreshTable();
            clearMarkFields();
        }
        catch (NumberFormatException exception)
        {
            showMessage("Enter valid numerical marks.");
        }
    }

    private void updateResult()
    {
        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null)
        {
            showMessage("Select a student and subject.");
            return;
        }

        try
        {
            double assignment = Double.parseDouble(
                assignmentField.getText().trim()
            );
            double test = Double.parseDouble(testField.getText().trim());
            double exam = Double.parseDouble(examField.getText().trim());

            if (!validMarks(assignment, test, exam))
            {
                showMessage("Marks must be between 0 and 100.");
                return;
            }

            if (!manager.updateStudentSubject(
                student.getStudentId(),
                subject.getSubjectCode(),
                assignment,
                test,
                exam
            ))
            {
                showMessage("Result was not found.");
                return;
            }

            refreshTable();
            clearMarkFields();
        }
        catch (NumberFormatException exception)
        {
            showMessage("Enter valid numerical marks.");
        }
    }

    private void deleteResult()
    {
        Student student = (Student) studentCombo.getSelectedItem();
        Subject subject = (Subject) subjectCombo.getSelectedItem();

        if (student == null || subject == null)
        {
            showMessage("Select a student and subject.");
            return;
        }

        if (!manager.deleteStudentSubject(
            student.getStudentId(),
            subject.getSubjectCode()
        ))
        {
            showMessage("Result was not found.");
            return;
        }

        refreshTable();
    }

    public void refreshAll()
    {
        studentCombo.removeAllItems();
        subjectCombo.removeAllItems();

        for (Student student : manager.getStudents())
        {
            studentCombo.addItem(student);
        }

        for (Subject subject : manager.getSubjects())
        {
            subjectCombo.addItem(subject);
        }

        refreshTable();
    }

    private void refreshTable()
    {
        tableModel.setRowCount(0);

        for (StudentSubject result : manager.getStudentSubjects())
        {
            tableModel.addRow(
                new Object[] {
                    result.getStudent().getStudentId(),
                    result.getStudent().getName(),
                    result.getSubject().getSubjectCode(),
                    result.getAssignmentMark(),
                    result.getTestMark(),
                    result.getFinalMarks(),
                    String.format("%.2f", result.getOverallMark()),
                    result.getGrade(),
                    result.getStatus()
                }
            );
        }
    }

    private boolean validMarks(double assignment, double test, double exam)
    {
        return GradeCalculate.isValidMark(assignment)
            && GradeCalculate.isValidMark(test)
            && GradeCalculate.isValidMark(exam);
    }

    private void clearMarkFields()
    {
        assignmentField.setText("");
        testField.setText("");
        examField.setText("");
    }

    private void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }

}
