package forms.admin;

import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class frmManageUsers extends JFrame {
    private JTable tblUsers;
    private JPanel pnlManage;
    private JTextField txtUsername;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnAddUser;
    private JButton btnEditUser;
    private JButton btnDeleteUser;

    public frmManageUsers(User loggedInUser) {
        super("Manage Users");
        setContentPane(pnlManage);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);

        if (!loggedInUser.getRole().equalsIgnoreCase("admin")) {
            JOptionPane.showMessageDialog(null, "Access Denied: You are not an admin!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setVisible(true);
        loadUserData();

        btnAddUser.addActionListener(_ -> {
            String username = txtUsername.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            DBAccess dbAccess = DBAccess.getInstance();

            if (!dbAccess.isUserNameUnique(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists.");
                return;
            }

            Customer newUser = new Customer(0, username, firstName, lastName, email, password) ;

            if (dbAccess.addUser(newUser, "customer")) {
                JOptionPane.showMessageDialog(null, "User added successfully.");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding user.");
            }
        });

        btnEditUser.addActionListener(_ -> {
            int selectedRow = tblUsers.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a user.");
                return;
            }

            int id = (int) tblUsers.getValueAt(selectedRow, 0);
            String username = txtUsername.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            DBAccess dbAccess = DBAccess.getInstance();

            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this user?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            Customer updatedUser = new Customer(id, username, firstName, lastName, email, password);

            if (dbAccess.updateUser(updatedUser)) {
                JOptionPane.showMessageDialog(null, "User successfully updated.");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating user.");
            }
        });

        btnDeleteUser.addActionListener(_ -> {
            int selectedRow = tblUsers.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a user.");
                return;
            }

            int id = (int) tblUsers.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            DBAccess dbAccess = DBAccess.getInstance();

            if (dbAccess.deleteUser(id)) {
                JOptionPane.showMessageDialog(null, "User deleted successfully.");
                loadUserData();
            } else {
                JOptionPane.showMessageDialog(null, "Error deleting user.");
            }
        });
        }

    public void loadUserData() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Username", "First Name", "Last Name", "Email"});

        try {
            DBAccess db = DBAccess.getInstance();
            List<User> users = db.getAllUsers();

            for (User user : users) {
                model.addRow(new Object[]{
                        user.getId(),
                        user.getUserName(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPassword(),
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error loading users.", JOptionPane.ERROR_MESSAGE);
        }
        tblUsers.setModel(model);
        tblUsers.revalidate();
        tblUsers.repaint();
    }
}