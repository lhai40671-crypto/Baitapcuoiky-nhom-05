package model;

public class Booking {
    private String bookingId;
    private Student student;
    private Room room;
    private TimeSlot timeSlot;
    private int participantCount;
    private String status;

    public Booking() {
    }

    public Booking(String bookingId, Student student, Room room,
                   TimeSlot timeSlot, int participantCount, String status) {
        this.bookingId = bookingId;
        this.student = student;
        this.room = room;
        this.timeSlot = timeSlot;
        this.participantCount = participantCount;
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

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Tính phí của lịch đặt
    public double calculateFee() {
        return room.calculateFee((int) timeSlot.getHours());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", student=" + student.getName() +
                ", room=" + room.getRoomName() +
                ", timeSlot=" + timeSlot +
                ", participantCount=" + participantCount +
                ", status='" + status + '\'' +
                '}';
    }
}
//lan 2