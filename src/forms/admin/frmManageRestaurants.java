package forms.admin;

import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class frmManageRestaurants extends JFrame {
    private JPanel pnlManage;
    private JTable tblRestaurants;
    private JTextField txtName;
    private JTextField txtCuisine;
    private JButton btnAddRestaurant;
    private JButton btnDeleteRestaurant;
    private JButton btnEditRestaurant;
    private JTextField txtLogo;

    public frmManageRestaurants(User loggedInUser) {
        super("Manage Restaurants");
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
        loadRestaurantData();

        btnAddRestaurant.addActionListener(_ -> {
            String name = txtName.getText().trim();
            String cuisine = txtCuisine.getText().trim();
            String logo = txtLogo.getText().trim();

            if (name.isEmpty() || cuisine.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            DBAccess dbAccess = DBAccess.getInstance();
            Restaurant newRestaurant = new Restaurant(0, name, cuisine, logo);

            if (dbAccess.addRestaurant(newRestaurant)) {
                JOptionPane.showMessageDialog(null, "Restaurant added successfully.");
                loadRestaurantData();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding restaurant.");
            }
        });

        btnEditRestaurant.addActionListener(_ -> {
            int selectedRow = tblRestaurants.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a restaurant.");
                return;
            }

            int id = (int) tblRestaurants.getValueAt(selectedRow, 0);
            String name = txtName.getText().trim();
            String cuisine = txtCuisine.getText().trim();
            String imageURL = txtLogo.getText().trim();

            if (name.isEmpty() || cuisine.isEmpty() || imageURL.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this restaurant?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            DBAccess dbAccess = DBAccess.getInstance();
            Restaurant updatedRestaurant = new Restaurant(id, name, cuisine, imageURL);

            if (dbAccess.updateRestaurant(updatedRestaurant)) {
                JOptionPane.showMessageDialog(null, "Restaurant updated successfully.");
                loadRestaurantData();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating restaurant.");
            }
        });

        btnDeleteRestaurant.addActionListener(_ -> {
            int selectedRow = tblRestaurants.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a restaurant.");
                return;
            }

            int id = (int) tblRestaurants.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this restaurant?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            DBAccess dbAccess = DBAccess.getInstance();

            if (dbAccess.deleteRestaurant(id)) {
                JOptionPane.showMessageDialog(null, "Restaurant deleted successfully.");
                loadRestaurantData();
            } else {
                JOptionPane.showMessageDialog(null, "Error deleting restaurant.");
            }
        });
    }

    public void loadRestaurantData() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Cuisine Type", "Logo"});

        try {
            DBAccess db = DBAccess.getInstance();
            List<Restaurant> restaurants = db.getAllRestaurants();

            for (Restaurant restaurant : restaurants) {
                model.addRow(new Object[]{
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getCuisine(),
                        restaurant.getImageURL()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error loading restaurants", JOptionPane.ERROR_MESSAGE);
        }

        tblRestaurants.setModel(model);
        tblRestaurants.revalidate();
        tblRestaurants.repaint();
    }
}