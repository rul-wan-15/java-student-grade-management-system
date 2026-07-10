package GUI;
import Service.GradeManager;
import Session.UserSession;
import Storage.DataFileHandler;
import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame
{
    private GradeManager manager;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private StudentPanel studentPanel;
    private SubjectPanel subjectPanel;
    private ResultPanel resultPanel;
    private ReportPanel reportPanel;

    public MainDashboard()
    {
        if (!UserSession.isLoggedIn())
        {
            new LoginFrame().setVisible(true);
            dispose();
            return;
        }

        manager = new GradeManager();
        DataFileHandler.loadAll(manager);

        setTitle("Student Grade Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildInterface();
    }

    private void buildInterface()
    {
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createNavigation(), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Student Grade Management System");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel user = new JLabel(
            "Welcome, " + UserSession.getCurrentUser().getUsername()
        );

        panel.add(title, BorderLayout.WEST);
        panel.add(user, BorderLayout.EAST);

        return panel;
    }

    private JPanel createNavigation()
    {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JButton studentButton = new JButton("Students");
        JButton subjectButton = new JButton("Subjects");
        JButton resultButton = new JButton("Results");
        JButton reportButton = new JButton("Student Report");
        JButton saveButton = new JButton("Save Data");
        JButton logoutButton = new JButton("Logout");

        studentButton.addActionListener(event ->
        {
            studentPanel.refreshTable();
            cardLayout.show(contentPanel, "STUDENT");
        });

        subjectButton.addActionListener(event ->
        {
            subjectPanel.refreshTable();
            cardLayout.show(contentPanel, "SUBJECT");
        });

        resultButton.addActionListener(event ->
        {
            resultPanel.refreshAll();
            cardLayout.show(contentPanel, "RESULT");
        });

        reportButton.addActionListener(event ->
        {
            reportPanel.refreshStudents();
            cardLayout.show(contentPanel, "REPORT");
        });

        saveButton.addActionListener(event ->
        {
            DataFileHandler.saveAll(manager);
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        });

        logoutButton.addActionListener(event -> logout());

        panel.add(studentButton);
        panel.add(subjectButton);
        panel.add(resultButton);
        panel.add(reportButton);
        panel.add(saveButton);
        panel.add(logoutButton);

        return panel;
    }

    private JPanel createContent()
    {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        studentPanel = new StudentPanel(manager);
        subjectPanel = new SubjectPanel(manager);
        resultPanel = new ResultPanel(manager);
        reportPanel = new ReportPanel(manager);

        contentPanel.add(studentPanel, "STUDENT");
        contentPanel.add(subjectPanel, "SUBJECT");
        contentPanel.add(resultPanel, "RESULT");
        contentPanel.add(reportPanel, "REPORT");

        return contentPanel;
    }

    private JPanel createFooter()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(
            new JLabel(
                "Logged in as: "
                + UserSession.getCurrentUser().getUsername()
                + " | Role: "
                + UserSession.getCurrentUser().getRole()
            )
        );

        return panel;
    }

    private void logout()
    {
        DataFileHandler.saveAll(manager);
        UserSession.logout();
        new LoginFrame().setVisible(true);
        dispose();
    }

}
