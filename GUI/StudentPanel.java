package GUI;
import Model.Student;
import Service.GradeManager;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class StudentPanel extends JPanel
{
    private GradeManager manager;
    private JTextField idField;
    private JTextField nameField;
    private DefaultTableModel tableModel;

    public StudentPanel(GradeManager manager)
    {
        this.manager = manager;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildInterface();
    }

    private void buildInterface()
    {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        idField = new JTextField();
        nameField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(event -> addStudent());
        updateButton.addActionListener(event -> updateStudent());
        deleteButton.addActionListener(event -> deleteStudent());
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
            new String[] {"Student ID", "Name"},
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
                idField.setText(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                idField.setEditable(false);
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();
    }

    private void addStudent()
    {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        if (id.isEmpty() || name.isEmpty())
        {
            showMessage("Student ID and name are required.");
            return;
        }

        if (!manager.addStudent(new Student(id, name)))
        {
            showMessage("Student ID already exists.");
            return;
        }

        refreshTable();
        clearFields();
    }

    private void updateStudent()
    {
        if (!manager.updateStudent(
            idField.getText().trim(),
            nameField.getText().trim()
        ))
        {
            showMessage("Student was not found.");
            return;
        }

        refreshTable();
        clearFields();
    }

    private void deleteStudent()
    {
        if (!manager.deleteStudent(idField.getText().trim()))
        {
            showMessage("Student was not found.");
            return;
        }

        refreshTable();
        clearFields();
    }

    public void refreshTable()
    {
        tableModel.setRowCount(0);

        for (Student student : manager.getStudents())
        {
            tableModel.addRow(
                new Object[] {student.getStudentId(), student.getName()}
            );
        }
    }

    private void clearFields()
    {
        idField.setText("");
        nameField.setText("");
        idField.setEditable(true);
    }

    private void showMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message);
    }

}
