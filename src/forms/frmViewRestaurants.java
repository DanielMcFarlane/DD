package forms;

import javax.swing.*;
import java.util.List;
import models.*;

public class frmViewRestaurants extends JFrame {
    private JPanel pnlContent;
    private List<Restaurant> restaurants;
    private JList<String> lstRestaurants;
    private JLabel lblName;
    private JLabel lblCuisine;
    private JButton btnViewMenu;
    private JLabel lblImage;

    public frmViewRestaurants() {
        super("View Restaurants");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        loadRestaurants();

        lstRestaurants.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayRestaurantDetails(lstRestaurants.getSelectedIndex());
            }
        });

        btnViewMenu.addActionListener(_ -> {
            int selectedIndex = lstRestaurants.getSelectedIndex();

            if (selectedIndex >= 0 && restaurants != null) {
                Restaurant selectedRestaurant = restaurants.get(selectedIndex);
                new frmViewMenu(selectedRestaurant.getId());
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a restaurant.");
            }
        });
    }

    private void loadRestaurants() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        DBAccess db = DBAccess.getInstance();
        restaurants = db.getAllRestaurants();

        for (Restaurant restaurant : restaurants) {
            listModel.addElement(restaurant.getName());
        }

        lstRestaurants.setModel(listModel);

        if (!listModel.isEmpty()) {
            lstRestaurants.setSelectedIndex(0);
            displayRestaurantDetails(0);
        }
    }

    private void displayRestaurantDetails(int i) {
        if (i >= 0 && i < restaurants.size()) {
            Restaurant selectedRestaurant = restaurants.get(i);

            lblName.setVisible(true);
            lblCuisine.setVisible(true);
            btnViewMenu.setVisible(true);
            lblImage.setVisible(true);

            lblName.setText(selectedRestaurant.getName());
            lblCuisine.setText(selectedRestaurant.getCuisine());

            ImageIcon imageIcon = new ImageIcon("src/images/default.jpg");
            lblImage.setIcon(imageIcon);
        }
    }
}