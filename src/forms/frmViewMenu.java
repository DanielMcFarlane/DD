package forms;

import javax.swing.*;
import models.*;
import java.util.List;

public class frmViewMenu extends JFrame {
    private JPanel pnlContent;
    private JEditorPane txtMenu;
    private JButton btnBack;

    public frmViewMenu(int restaurantId) {
        super("View Menu");
        setContentPane(pnlContent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        btnBack.addActionListener(e -> {
            new frmViewRestaurants();
            dispose();
        });

        loadMenuData(restaurantId);
    }

    private void loadMenuData(int restaurantId) {
        DBAccess db = DBAccess.getInstance();
        Menu menu = db.getMenu(restaurantId);

        if (menu == null) {
            txtMenu.setText("No menu found.");
            return;
        }

        List<MenuItem> menuItems = db.getMenuItems(menu.getId());
        StringBuilder menuText = new StringBuilder();

        menuText.append("<b>FOOD</b><br>").append("——————<br>");

        for (MenuItem item : menuItems) {
            if (item instanceof FoodItem) {
                menuText.append(item.getName())
                        .append(" - £").append(item.getPrice())
                        .append("<br>")
                        .append("<i>Serving Size: ").append(((FoodItem) item).getServingSize()).append("</i>")
                        .append("<br><br>");
            }
        }

        menuText.append("<b>DRINKS</b><br>").append("——————<br>");

        for (MenuItem item : menuItems) {
            if (item instanceof DrinkItem) {
                DrinkItem drink = (DrinkItem) item;
                menuText.append(drink.getName())
                        .append(" - £").append(drink.getPrice())
                        .append("<br>");

                if (drink.isAlcoholic()) {
                    menuText.append("<i>Alcoholic</i><br>");
                }
                menuText.append("<br>");
            }
        }

        txtMenu.setContentType("text/html"); // I found out you could format a JEditorPane to use HTML syntax
        txtMenu.setText(menuText.toString());
    }
}