package models;

public class Customer extends User {

    public Customer(int id, String userName, String firstName, String lastName, String email, String password) {
        super(id, userName, firstName, lastName, email, password, "Customer", null);
    }

    public boolean changePassword(DBAccess db, String oldPassword, String newPassword) {
        if (this.getPassword().equals(oldPassword)) {
            return db.updatePassword(this.getId(), newPassword);
        }
        return false;
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}