package forms;

import models.*;
import javax.swing.*;

public class frmLogin extends JFrame {
    private JPanel pnlContent;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton btnHome;

    public frmLogin() {
        super("Login Page");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(_ -> loginUser());
        btnHome.addActionListener(_ -> {new frmLandingPage(); dispose();});
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        DBAccess db = DBAccess.getInstance();
        boolean isAuthenticated = db.authenticateUser(username, password);

        if (isAuthenticated) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            User loggedInUser = db.getUser(username);
            new frmHomePage(loggedInUser);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}