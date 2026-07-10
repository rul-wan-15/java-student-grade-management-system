package GUI;
import Model.Student;
import Model.StudentSubject;
import Service.GradeManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportPanel extends JPanel
{
     private GradeManager manager;
    private JComboBox<Student> studentCombo;
    private JLabel gpaLabel;
    private DefaultTableModel tableModel;

    public ReportPanel(GradeManager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildInterface();
    }

    private void buildInterface()
    {
        studentCombo = new JComboBox<Student>();
        JButton viewButton = new JButton("View Report");
        gpaLabel = new JLabel("GPA: 0.00");

        viewButton.addActionListener(event -> displayReport());

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Student:"));
        controlPanel.add(studentCombo);
        controlPanel.add(viewButton);
        controlPanel.add(gpaLabel);

        tableModel = new DefaultTableModel(
            new String[] {
                "Subject Code",
                "Subject Name",
                "Credit Hour",
                "Overall",
                "Grade",
                "Grade Point",
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

        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        refreshStudents();
    }

    public void refreshStudents()
    {
        studentCombo.removeAllItems();

        for (Student student : manager.getStudents())
        {
            studentCombo.addItem(student);
        }

        displayReport();
    }

    private void displayReport()
    {
        tableModel.setRowCount(0);

        Student student = (Student) studentCombo.getSelectedItem();

        if (student == null)
        {
            gpaLabel.setText("GPA: 0.00");
            return;
        }

        for (StudentSubject result :
            manager.findResultByStudent(student.getStudentId()))
        {
            tableModel.addRow(
                new Object[] {
                    result.getSubject().getSubjectCode(),
                    result.getSubject().getSubjectName(),
                    result.getSubject().getCreditHour(),
                    String.format("%.2f", result.getOverallMark()),
                    result.getGrade(),
                    String.format("%.2f", result.getGradePoint()),
                    result.getStatus()
                }
            );
        }

        gpaLabel.setText(
            "GPA: "
            + String.format(
                "%.2f",
                manager.calculateStudentGPA(student.getStudentId())
            )
        );
    }

}
