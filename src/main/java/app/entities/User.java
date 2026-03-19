package app.entities;

public class User {
    int userId;
    String userName;
    String password;

    public User(int id, String userName, String password) {
        this.userId = id;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
