package GUI;
import Model.Subject;
import Service.GradeManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class SubjectPanel extends JPanel
{
    private GradeManager manager;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField creditField;
    private DefaultTableModel tableModel;

    public SubjectPanel(GradeManager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildInterface();
    }

    private void buildInterface()
    {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        codeField = new JTextField();
        nameField = new JTextField();
        creditField = new JTextField();

        formPanel.add(new JLabel("Subject Code:"));
        formPanel.add(codeField);
        formPanel.add(new JLabel("Subject Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Credit Hour:"));
        formPanel.add(creditField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(event -> addSubject());
        updateButton.addActionListener(event -> updateSubject());
        deleteButton.addActionListener(event -> deleteSubject());
        clearButton.addActionListener(event -> clearFields());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
            new String[] {"Code", "Subject Name", "Credit Hour"},
            0
        )
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(event ->
        {
            int row = table.getSelectedRow();

            if (row >= 0)
            {
                codeField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                creditField.setText(tableModel.getValueAt(row, 2).toString());
                codeField.setEditable(false);
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private void addSubject()
    {
        try
        {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            int creditHour = Integer.parseInt(creditField.getText().trim());

            if (code.isEmpty() || name.isEmpty() || creditHour <= 0)
            {
                showMessage("Enter valid subject information.");
                return;
            }

            if (!manager.addSubject(new Subject(code, name, creditHour)))
            {
                showMessage("Subject code already exists.");
                return;
            }

            refreshTable();
            clearFields();
        }
        catch (NumberFormatException exception)
        {
            showMessage("Credit hour must be a whole number.");
        }
    }

    private void updateSubject()
    {
        try
        {
            int creditHour = Integer.parseInt(creditField.getText().trim());

            if (!manager.updateSubject(
                codeField.getText().trim(),
                nameField.getText().trim(),
                creditHour
            ))
            {
                showMessage("Subject was not found.");
                return;
            }

            refreshTable();
            clearFields();
        }
        catch (NumberFormatException exception)
        {
            showMessage("Credit hour must be a whole number.");
        }
    }

    private void deleteSubject()
    {
        if (!manager.deleteSubject(codeField.getText().trim()))
        {
            showMessage("Subject was not found.");
            return;
        }

        refreshTable();
        clearFields();
    }

    public void refreshTable()
    {
        tableModel.setRowCount(0);

        for (Subject subject : manager.getSubjects())
        {
            tableModel.addRow(
                new Object[] {
                    subject.getSubjectCode(),
                    subject.getSubjectName(),
                    subject.getCreditHour()
                }
            );
        }
    }

    private void clearFields()
    {
        codeField.setText("");
        nameField.setText("");
        creditField.setText("");
        codeField.setEditable(true);
    }

    private void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }
}
