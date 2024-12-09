package entities;

public class User {
    // Attributes
    private int id;
    private String fullName;
    private String email;
    private String password;
    private long phoneNumber;

    // Constructor
    public User(int id, String fullName, String email, String password, long phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // setters and getters
    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }


    // methods
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully!");
    }

    public void updateProfile(String newFullName, String newEmail, long newPhoneNumber) {
        this.fullName = newFullName;
        this.email = newEmail;
        this.phoneNumber = newPhoneNumber;
        System.out.println("Profile updated successfully!");
    }

    public void viewAccountDetails() {
        System.out.println("User Details:");
        System.out.println("ID: " + id);
        System.out.println("Name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
    }

    public void updateUser(User updatedUser) {
        this.id = updatedUser.id;
        this.fullName = updatedUser.fullName;
        this.email = updatedUser.email;
        this.password = updatedUser.password;
        this.phoneNumber = updatedUser.phoneNumber;
        System.out.println("User updated successfully!");
    }
}
