package models;

public class Admin extends User {

    public Admin(int id, String userName, String firstName, String lastName, String email, String password) {
        super(id, userName, firstName, lastName, email, password, "Admin", null);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}