package forms;

import forms.admin.frmManageRestaurants;
import forms.admin.frmManageUsers;
import forms.staff.frmManageMenu;
import models.DBAccess;
import models.User;
import java.awt.*;
import javax.swing.*;

public class frmHomePage extends JFrame {
    private JPanel pnlContent;
    private JLabel lblPhoto;
    private JLabel lblWelcome;
    private JLabel lblRole;
    private JButton btn1;
    private JButton btn2;
    private JButton btnLogout;

    public frmHomePage(User loggedInUser) {
        super("Home Page");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        lblWelcome.setText("Welcome " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        lblRole.setText("Role: " + loggedInUser.getRoleTitle());

        String role = loggedInUser.getRole().trim().toLowerCase();

        switch (role) {
            case "admin":
                lblPhoto.setIcon(new ImageIcon("src/images/admin.jpg"));
                btn1.setText("Manage Users");
                btn2.setText("Manage Restaurants");
                btn1.addActionListener(_ -> new frmManageUsers(loggedInUser));
                btn2.addActionListener(_ -> new frmManageRestaurants(loggedInUser));
                break;

            case "customer":
                lblPhoto.setIcon(new ImageIcon("src/images/customer.jpg"));
                btn1.setText("View Restaurants");
                btn2.setText("Manage Account");
                btn1.addActionListener(_ -> new frmViewRestaurants());
                btn2.addActionListener(_ -> new frmManageAccount(loggedInUser));
                break;

            case "staff":
                String userName = loggedInUser.getUserName().trim().toLowerCase();
                lblPhoto.setIcon(new ImageIcon(userName.equals("ron") ? "src/images/ron.jpg" : "src/images/staff.jpg"));
                btn1.setText("Manage Menu");
                btn2.setVisible(false);
                btn1.addActionListener(_ -> new frmManageMenu(loggedInUser));
                break;

            default:
                lblPhoto.setIcon(new ImageIcon("src/images/500x300.png"));
                btn1.setVisible(false);
                btn2.setVisible(false);
        }

        btnLogout.addActionListener(e -> {
            for (Window window : Window.getWindows()) {
                if (window instanceof JFrame && window.isShowing()) {
                    window.dispose();
                }
            }

            DBAccess.getInstance().disconnect();
            new frmLogin();
        });
    }
}