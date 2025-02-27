package forms.staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.*;
import java.util.List;

public class frmManageMenu extends JFrame {
    private JPanel pnlManage;
    private JTable tblMenu;
    private JTextField txtName;
    private JTextField txtPrice;
    private JTextField txtDescription;
    private JTextField txtServingSize;
    private JRadioButton rbtnYes;
    private JRadioButton rbtnNo;
    private JRadioButton rbtnFood;
    private JRadioButton rbtnDrink;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private int restaurantId;

    public frmManageMenu(User loggedInUser) {
        super("Manage Menu");
        setContentPane(pnlManage);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);

        if (!(loggedInUser instanceof Staff)) {
            JOptionPane.showMessageDialog(null, "Access Denied: You are not a member of staff.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        if ((this.restaurantId = loggedInUser.getRestaurantId()) == 0) {
            JOptionPane.showMessageDialog(null, "Error: No restaurant assigned to this staff.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setVisible(true);
        loadMenuData();

        rbtnFood.addActionListener(_ -> {
            rbtnDrink.setSelected(false);
            rbtnYes.setEnabled(false);
            rbtnNo.setEnabled(false);
            rbtnYes.setSelected(false);
            rbtnNo.setSelected(false);
        });

        rbtnDrink.addActionListener(_ -> {
            rbtnFood.setSelected(false);
            rbtnYes.setEnabled(true);
            rbtnNo.setEnabled(true);
        });

        rbtnYes.addActionListener(_ -> rbtnNo.setSelected(false));
        rbtnNo.addActionListener(_ -> rbtnYes.setSelected(false)); // Messy but works

        btnAdd.addActionListener(_ -> {
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            String servingSize = txtServingSize.getText().trim();

            if (name.isEmpty() || description.isEmpty() || servingSize.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            double price;

            try {
                price = Double.parseDouble(txtPrice.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price.");
                return;
            }

            boolean isAlcoholic = rbtnYes.isSelected();
            boolean isFood = rbtnFood.isSelected();

            DBAccess dbAccess = DBAccess.getInstance();
            Menu menu = dbAccess.getMenu(restaurantId);

            if (menu == null) {
                JOptionPane.showMessageDialog(null, "Error: No menu found for this restaurant.");
                return;
            }

            MenuItem newItem = isFood ?
                    new FoodItem(0, menu.getId(), name, price, 1, servingSize, description) :
                    new DrinkItem(0, menu.getId(), name, price, 1, servingSize, isAlcoholic, description);

            if (dbAccess.addMenuItem(newItem)) {
                JOptionPane.showMessageDialog(null, "Menu item added successfully.");
                loadMenuData();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding menu item.");
            }
        });

        btnEdit.addActionListener(_ -> {
            int selectedRow = tblMenu.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a menu item.");
                return;
            }

            int id = (int) tblMenu.getValueAt(selectedRow, 0);
            String name = txtName.getText().trim();
            String description = txtDescription.getText().trim();
            String servingSize = txtServingSize.getText().trim();

            if (name.isEmpty() || description.isEmpty() || servingSize.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            double price;

            try {
                price = Double.parseDouble(txtPrice.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price.");
                return;
            }

            boolean isAlcoholic = rbtnYes.isSelected();
            boolean isFood = rbtnFood.isSelected();

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this menu item?",
                    "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            DBAccess dbAccess = DBAccess.getInstance();
            Menu menu = dbAccess.getMenu(restaurantId);

            if (menu == null) {
                JOptionPane.showMessageDialog(null, "Error: No menu found for this restaurant.");
                return;
            }

            MenuItem updatedItem = isFood
                    ? new FoodItem(id, menu.getId(), name, price, 1, servingSize, description)
                    : new DrinkItem(id, menu.getId(), name, price, 1, servingSize, isAlcoholic, description);

            if (dbAccess.updateMenuItem(updatedItem)) {
                JOptionPane.showMessageDialog(null, "Menu item updated successfully.");
                loadMenuData();
            } else {
                JOptionPane.showMessageDialog(null, "Error updating menu item.");
            }
        });

        btnDelete.addActionListener(_ -> {
            int selectedRow = tblMenu.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a menu item.");
                return;
            }

            int id = (int) tblMenu.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this menu item?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            DBAccess dbAccess = DBAccess.getInstance();

            if (dbAccess.deleteMenuItem(id)) {
                JOptionPane.showMessageDialog(null, "Menu item deleted successfully.");
                loadMenuData();
            } else {
                JOptionPane.showMessageDialog(null, "Error deleting menu item.");
            }
        });

    }

    private void loadMenuData() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Price", "Type", "Description", "Serving Size", "Alcoholic"});

        try {
            DBAccess db = DBAccess.getInstance();
            Menu menu = db.getMenu(restaurantId);

            if (menu == null) {
                JOptionPane.showMessageDialog(null, "Error: Menu not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<MenuItem> menuItems = db.getMenuItems(menu.getId());

            for (MenuItem item : menuItems) {
                model.addRow(new Object[]{
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        (item instanceof FoodItem) ? "Food" : "Drink",
                        item.getDescription(),
                        (item instanceof FoodItem) ? ((FoodItem) item).getServingSize() : ((DrinkItem) item).getServingSize(),
                        (item instanceof DrinkItem) ? ((DrinkItem) item).isAlcoholic() : "N/A"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching menu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        tblMenu.setModel(model);
        tblMenu.revalidate();
        tblMenu.repaint();
    }
}
