package model;

public class User {
    private String id;
    private String fullName;
    private String phone;
    private String className;
    private String email;

    public User() {
    }

    public User(String id, String fullName, String phone,
                String className, String email) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.className = className;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}