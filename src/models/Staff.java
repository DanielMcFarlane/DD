package models;

public class Staff extends User {
    private String restaurantName;

    public Staff(int id, String userName, String firstName, String lastName, String email, String password, Integer restaurantId, String restaurantName) {
        super(id, userName, firstName, lastName, email, password, "Staff", restaurantId);
        this.restaurantName = restaurantName == null ? "" : restaurantName;  // Ternary to default to an empty string if restaurantName is null
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public String getRole() {
        return "Staff";
    }

    @Override
    public String getRoleTitle() {
        return getUserName().equalsIgnoreCase("ron") ? "Owner" : restaurantName + " Staff";
    } // My switch case on frmHomePage wouldn't work without this.
}

/*
    I was trying to display either owner or staff on frmHomePage if an owner logged in, but it would only work with an if statement.
    (It would always return the default clause with a switch case, even when it was just staff it defaulted. I couldn't find why.)
    The only way I got it to work was using the username and ternary. I removed it from my sql entirely but kept the logic for ron.
*/