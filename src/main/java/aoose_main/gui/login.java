package aoose_main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;
    private JButton loginButton;

    // Constructor
    public login() {
        // Initialize the panel and disable layout manager
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(Color.LIGHT_GRAY); // Set background color to light blue

        // Email input
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 30, 100, 30);
        panel1.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 30, 200, 30);
        panel1.add(emailField);

        // Password input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 100, 30);
        panel1.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 200, 30);
        panel1.add(passwordField);

        // User type dropdown
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setBounds(50, 130, 100, 30);
        panel1.add(userTypeLabel);

        userTypeComboBox = new JComboBox<>(new String[]{
                "Patient", "Insurance Provider", "Inventory Clerk", "Pharmacist", "Admin", "Supplier"
        });
        userTypeComboBox.setBounds(150, 130, 200, 30);
        panel1.add(userTypeComboBox);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 180, 100, 30);
        panel1.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String userType = (String) userTypeComboBox.getSelectedItem();

                // Perform login logic
                if (authenticateUser(email, password, userType)) {
                    JOptionPane.showMessageDialog(null, "Login successful as " + userType);
                    // Add logic to redirect to the appropriate dashboard
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials or user type", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Simple authentication logic for demonstration purposes
    private boolean authenticateUser(String email, String password, String userType) {
        // Replace this logic with database integration
        return email.equals("admin@example.com") && password.equals("admin123") && userType.equals("Admin");
    }

    public JPanel getPanel() {
        return panel1;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        login loginForm = new login();

        frame.setContentPane(loginForm.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
