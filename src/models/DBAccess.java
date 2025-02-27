package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAccess {
    private static final String URL = "jdbc:h2:file:./DineDashDB/dinedash.db";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static DBAccess instance;
    private Connection connection;

    private DBAccess() {
        connect();
    }

    public static DBAccess getInstance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error obtaining database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connection established.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
                instance = null;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // =============================================
    // Methods relating to users
    // =============================================


    public boolean isUserNameUnique(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Username check error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Authentication error: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean addUser(User user, String role) {
        String sql = "INSERT INTO users (username, first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, role.toLowerCase());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Add user error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User updatedUser) {
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedUser.getUserName());
            stmt.setString(2, updatedUser.getFirstName());
            stmt.setString(3, updatedUser.getLastName());
            stmt.setString(4, updatedUser.getEmail());
            stmt.setString(5, updatedUser.getPassword());
            stmt.setInt(6, updatedUser.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public User getUser(String username) {
        String sql = "SELECT users.*, restaurants.name AS restaurant_name "
                   + "FROM users "
                   + "LEFT JOIN restaurants ON users.restaurant_id = restaurants.id "
                   + "WHERE users.username = ?"; // Used a left join to combine the two tables.

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    switch (rs.getString("role").toLowerCase()) {
                        case "customer":
                            return new Customer(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password")
                            );

                        case "staff":
                            return new Staff(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password"),
                                    rs.getObject("restaurant_id") == null ? null : rs.getInt("restaurant_id"),
                                    rs.getString("restaurant_name")
                            );

                        case "admin":
                            return new Admin(
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("password")
                            );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Authentication error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT users.id, users.username, users.first_name, users.last_name, users.role, users.email, users.password, restaurants.name AS restaurant_name "
                + "FROM users "
                + "LEFT JOIN restaurants ON users.restaurant_id = restaurants.id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String restaurantName = rs.getString("restaurant_name");

                User user;
                user = switch (role.toLowerCase()) {
                    case "admin" -> new Admin(id, username, firstName, lastName, email, password);
                    case "customer" -> new Customer(id, username, firstName, lastName, email, password);
                    case "staff", "restaurant" -> new Staff(id, username, firstName, lastName, email, password, 0, restaurantName);
                    default -> null;
                };

                if (user != null) {
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Fetch users error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ? AND LOWER(role) = 'customer'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    // =============================================
    // Methods relating to Restaurants
    // =============================================


    public boolean addRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (name, cuisine) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getName());
            stmt.setString(2, restaurant.getCuisine());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding restaurant: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRestaurant(Restaurant updatedRestaurant) {
        String sql = "UPDATE restaurants SET name = ?, cuisine = ?, image_url = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedRestaurant.getName());
            stmt.setString(2, updatedRestaurant.getCuisine());
            stmt.setString(3, updatedRestaurant.getImageURL());
            stmt.setInt(4, updatedRestaurant.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating restaurant: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurants WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting restaurant: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Restaurant getRestaurant(int id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Restaurant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("cuisine"),
                        rs.getString("image_url")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching restaurant: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                restaurants.add(new Restaurant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("cuisine"),
                        rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all restaurants " + e.getMessage());
            e.printStackTrace();
        }
        return restaurants;
    }


    // =============================================
    // Methods relating to Menu
    // =============================================


    public boolean addMenuItem(MenuItem item) {
        String sql = "INSERT INTO menu_items (menu_id, name, price, type, description, serving_size, is_alcoholic) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getMenuId());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.setString(4, (item instanceof FoodItem) ? "Food" : "Drink"); // Used ternary to avoid errors
            stmt.setString(5, item.getDescription());
            stmt.setString(6, (item instanceof FoodItem) ? ((FoodItem) item).getServingSize() : ((DrinkItem) item).getServingSize());
            stmt.setObject(7, (item instanceof DrinkItem) ? ((DrinkItem) item).isAlcoholic() : null);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding menu item: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMenuItem(MenuItem item) {
        String sql = "UPDATE menu_items SET name = ?, price = ?, type = ?, description = ?, serving_size = ?, is_alcoholic = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, (item instanceof FoodItem) ? "Food" : "Drink"); // Used ternary to avoid errors
            stmt.setString(4, item.getDescription());
            stmt.setString(5, (item instanceof FoodItem) ? ((FoodItem) item).getServingSize() : ((DrinkItem) item).getServingSize());
            stmt.setObject(6, (item instanceof DrinkItem) ? ((DrinkItem) item).isAlcoholic() : null);
            stmt.setInt(7, item.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating menu item: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMenuItem(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting menu item: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Menu getMenu(int restaurantId) {
        String sql = "SELECT * FROM menus WHERE restaurant_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Menu(
                            rs.getInt("id"),
                            rs.getInt("restaurant_id"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching menu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<MenuItem> getMenuItems(int restaurantId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE menu_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, restaurantId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String servingSize = rs.getString("serving_size");
                    String description = rs.getString("description");
                    boolean isAlcoholic = rs.getBoolean("is_alcoholic");
                    String type = rs.getString("type").toLowerCase(); // The menu doesn't exist without toLowerCase

                    MenuItem item = switch (type) {
                        case "food" -> new FoodItem(id, restaurantId, name, price, 1, servingSize, description);
                        case "drink" -> new DrinkItem(id, restaurantId, name, price, 1, servingSize, isAlcoholic, description);
                        default -> null;
                    };

                    if (item != null) {
                        menuItems.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching menu items: " + e.getMessage());
            e.printStackTrace();
        }
        return menuItems;
    }
}