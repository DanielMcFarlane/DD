package models;

public abstract class MenuItem {
    private int id;
    private int menuId;
    private String name;
    private double price;
    private int quantity;
    private String description;

    public MenuItem(int id, int menuId, String name, double price, int quantity, String description) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}