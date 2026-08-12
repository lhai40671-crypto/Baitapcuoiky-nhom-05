package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String studentId;
    private String className;
    private List<Booking> bookings;

    public Student() {
        bookings = new ArrayList<>();
    }

    public Student(String studentId,
                   String fullName,
                   String phone,
                   String className,
                   String email) {

        super(studentId, fullName, phone, email);

        this.studentId = studentId;
        this.className = className;
        this.bookings = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking != null) {
            bookings.add(booking);
        }
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    @Override
    public void displayInfo() {
        System.out.println("===== THÔNG TIN SINH VIÊN =====");
        System.out.println("Mã sinh viên: " + studentId);
        System.out.println("Họ tên: " + getFullName());
        System.out.println("Số điện thoại: " + getPhone());
        System.out.println("Lớp: " + className);
        System.out.println("Email: " + getEmail());
    }

    @Override
    public String toString() {
        return studentId + " | "
                + getFullName() + " | "
                + getPhone() + " | "
                + className + " | "
                + getEmail();
    }
}