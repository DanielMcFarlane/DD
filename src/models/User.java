package models;

public abstract class User {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Integer restaurantId;

    public User(int id, String userName, String firstName, String lastName, String email, String password, String role, Integer restaurantId) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.restaurantId = restaurantId;
        this.role = (role == null) ? "" : role; // Ternary to default to an empty string if role is null
        // This lets you drop the extra constructor without everything imploding
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRoleTitle() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName - " + userName + '\'' +
                ", firstName - " + firstName + '\'' +
                ", lastName - " + lastName + '\'' +
                ", email - " + email + '\'' +
                ", password - " + password + '\'' +
                (restaurantId == null ? "" :  ", restaurantId=" + restaurantId) +
                '}';
    }
}