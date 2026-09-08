package model;

/**
 * Lớp Student đại diện cho sinh viên đặt phòng.
 * Student kế thừa các thông tin chung từ User.
 */
public class Student extends User {

    private String className;

    /**
     * Constructor mặc định.
     */
    public Student() {
        super();
    }

    /**
     * Constructor đầy đủ thông tin sinh viên.
     */
    public Student(String studentId,
                   String name,
                   String phone,
                   String className,
                   String email) {

        super(studentId, name, phone, email);
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

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