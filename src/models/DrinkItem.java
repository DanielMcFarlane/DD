package models;

public class DrinkItem extends MenuItem {
    private String servingSize;
    private boolean isAlcoholic;

    public DrinkItem(int id, int menuId, String name, double price, int quantity, String servingSize, boolean isAlcoholic, String description) {
        super(id, menuId, name, price, quantity, description);
        this.servingSize = servingSize;
        this.isAlcoholic = isAlcoholic;
    }

    public String getServingSize() {
        return servingSize;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }
}