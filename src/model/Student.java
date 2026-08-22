package model;

public class Student extends User {

    private String className;

    // Constructor mặc định
    public Student() {
        super();
    }

    // Constructor đầy đủ
    public Student(String studentId,
                   String name,
                   String phone,
                   String className,
                   String email) {

        super(studentId, name, phone, email);
        this.className = className;
    }

    // Getter
    public String getClassName() {
        return className;
    }

    // Setter
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + getUserId() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", className='" + className + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}