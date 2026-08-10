package model;

public class Student extends User {

    public Student() {
        super();
    }

    public Student(String id, String fullName, String phone,
                   String className, String email) {
        super(id, fullName, phone, className, email);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", className='" + getClassName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}