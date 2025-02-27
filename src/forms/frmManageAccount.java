package forms;

import javax.swing.*;
import models.*;

public class frmManageAccount extends JFrame {
    private JPanel pnlContent;
    private JPasswordField txtCurrentPass;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;
    private JButton btnCancel;
    private JButton btnConfirm;
    private JTextField txtEmail;

    public frmManageAccount(User loggedInUser) {
        super("Manage Account");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);


        btnConfirm.addActionListener(_ -> {
            String email = txtEmail.getText().trim();
            String currentPass = new String(txtCurrentPass.getPassword());
            String newPass = new String(txtNewPass.getPassword());
            String confirmPass = new String(txtConfirmPass.getPassword());

            if (email.isEmpty() || !email.equalsIgnoreCase(loggedInUser.getEmail())) {
                showMessage(email.isEmpty() ? "Please enter an email." : "The email you entered is does not exist.");
                return;
            }

            if (currentPass.isEmpty() || !currentPass.equals(loggedInUser.getPassword())) {
                showMessage(currentPass.isEmpty() ? "Please enter your current password." : "The current password does not exist.");
                return;
            }

            if (newPass.isEmpty() || newPass.equals(currentPass)) {
                showMessage(newPass.isEmpty() ? "Please enter a new password." : "The new password cannot be the same as your old password.");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                showMessage("The new password and confirmation password do not match.");
                return;
            }

            DBAccess db = DBAccess.getInstance();

            if (loggedInUser instanceof Customer customer) {
                if (customer.changePassword(db, currentPass, newPass)) {
                    showMessage("Your password has been updated successfully.");
                    dispose();
                } else {
                    showMessage("There was an error updating your password.");
                }
            }
        });

        btnCancel.addActionListener(_ -> dispose());
    }

    private void showMessage(String message) { // Trying out using helper methods with ternary
        JOptionPane.showMessageDialog(this, message);
    }
}