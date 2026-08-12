package model;

import java.time.LocalDateTime;

public class Booking {

    private String bookingId;
    private Student student;
    private Room room;
    private TimeSlot timeSlot;
    private BookingDetail detail;
    private String status;

    public Booking() {
    }

    public Booking(String bookingId,
                   Student student,
                   Room room,
                   LocalDateTime startTime,
                   LocalDateTime endTime,
                   int numberOfPeople) {

        this.bookingId = bookingId;
        this.student = student;
        this.room = room;

        this.timeSlot =
                new TimeSlot(startTime, endTime);

        this.detail =
                new BookingDetail(
                        numberOfPeople,
                        "Học nhóm",
                        "");

        this.status = "Đã đặt";
    }

    public Booking(String bookingId,
                   Student student,
                   Room room,
                   TimeSlot timeSlot,
                   BookingDetail detail,
                   String status) {

        this.bookingId = bookingId;
        this.student = student;
        this.room = room;
        this.timeSlot = timeSlot;
        this.detail = detail;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public BookingDetail getDetail() {
        return detail;
    }

    public void setDetail(BookingDetail detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return timeSlot.getStartTime();
    }

    public LocalDateTime getEndTime() {
        return timeSlot.getEndTime();
    }

    public int getNumberOfPeople() {
        return detail.getNumberOfPeople();
    }

    public double getHours() {
        return timeSlot.getHours();
    }

    public double calculateFee() {
        return room.getPricePerHour()
                * getHours();
    }

    @Override
    public String toString() {

        return "\n===== BOOKING ====="
                + "\nMã đặt: " + bookingId
                + "\nSinh viên: "
                + student.getFullName()
                + " (" + student.getStudentId() + ")"
                + "\nPhòng: " + room.getRoomId()
                + " - " + room.getRoomName()
                + "\nThời gian: " + timeSlot
                + "\nSố người: " + getNumberOfPeople()
                + "\nTrạng thái: " + status
                + "\nPhí: " + calculateFee()
                + " VNĐ";
    }
}
// Update lan 3