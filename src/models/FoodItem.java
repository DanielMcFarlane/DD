package models;

public class FoodItem extends MenuItem {
    private String servingSize;

    public FoodItem(int id, int menuId, String name, double price, int quantity, String servingSize, String description) {
        super(id, menuId, name, price, quantity, description);
        this.servingSize = servingSize;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }
}