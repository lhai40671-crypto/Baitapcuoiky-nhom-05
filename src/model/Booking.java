package model;

public class Booking {

    private String bookingId;
    private Student student;
    private Room room;
    private TimeSlot timeSlot;
    private int participantCount;
    private String status;

    // Constructor mặc định
    public Booking() {
    }

    // Constructor đầy đủ
    public Booking(String bookingId,
                   Student student,
                   Room room,
                   TimeSlot timeSlot,
                   int participantCount,
                   String status) {

        this.bookingId = bookingId;
        this.student = student;
        this.room = room;
        this.timeSlot = timeSlot;
        this.participantCount = participantCount;
        this.status = status;
    }

    // Getter
    public String getBookingId() {
        return bookingId;
    }

    public Student getStudent() {
        return student;
    }

    public Room getRoom() {
        return room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Tính phí của lịch đặt
    public double calculateFee() {

        if (room == null || timeSlot == null) {
            return 0;
        }

        int hours = (int) timeSlot.getHours();

        if (hours <= 0) {
            return 0;
        }

        return room.calculateFee(hours);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", student=" +
                (student != null ? student.getName() : "null") +
                ", room=" +
                (room != null ? room.getRoomName() : "null") +
                ", timeSlot=" + timeSlot +
                ", participantCount=" + participantCount +
                ", status='" + status + '\'' +
                '}';
    }
}//