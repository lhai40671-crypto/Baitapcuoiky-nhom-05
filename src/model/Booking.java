package model;

/**
 * Lớp Booking đại diện cho một lịch đặt phòng.
 */
public class Booking {

    /**
     * Trạng thái lịch đã đặt.
     */
    public static final String STATUS_BOOKED = "Đã đặt";

    /**
     * Trạng thái lịch đã hủy.
     */
    public static final String STATUS_CANCELED = "Đã hủy";

    private String bookingId;
    private Student student;
    private Room room;
    private TimeSlot timeSlot;
    private int participantCount;
    private String status;

    /**
     * Constructor mặc định.
     */
    public Booking() {
    }

    /**
     * Constructor đầy đủ thông tin booking.
     */
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

    /**
     * Kiểm tra booking đã bị hủy chưa.
     */
    public boolean isCanceled() {
        return STATUS_CANCELED.equalsIgnoreCase(status)
                || "Canceled".equalsIgnoreCase(status);
    }

    /**
     * Kiểm tra booking đang ở trạng thái đã đặt.
     */
    public boolean isBooked() {
        return STATUS_BOOKED.equalsIgnoreCase(status)
                || "Booked".equalsIgnoreCase(status);
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
}