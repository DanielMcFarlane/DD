package models;

public class Menu {
    private int id;
    private int restaurantId;
    private String name;

    public Menu(int id, int restaurantId, String name) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}