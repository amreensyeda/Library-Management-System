package BookSpehere2;

import java.io.Serializable;

class User implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID
    private static int nextUserId = 1;
    private int userId;
    private String role;  // "Admin" or "Student"

    public User(String role) {
        this.userId = nextUserId++;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
