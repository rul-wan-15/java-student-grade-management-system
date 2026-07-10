package GUI;
import Model.User;
import Session.UserSession;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame
{
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame()
    {
        setTitle("Student Grade System - Login");
        setSize(420, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildInterface();
    }

    private void buildInterface()
    {
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
            "Student Grade Management System",
            SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 35, 20, 35)
        );

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        loginButton.addActionListener(event -> login());
        clearButton.addActionListener(event ->
        {
            usernameField.setText("");
            passwordField.setText("");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);

        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void login()
    {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin123"))
        {
            UserSession.login(new User(username, "Administrator"));
            new MainDashboard().setVisible(true);
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(
                this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
