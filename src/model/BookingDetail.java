package model;

public class BookingDetail {

    private Booking booking;
    private int participantCount;
    private double totalFee;
    private String note;

    // Constructor mặc định
    public BookingDetail() {
    }

    // Constructor đầy đủ
    public BookingDetail(Booking booking,
                         int participantCount,
                         double totalFee,
                         String note) {

        this.booking = booking;
        this.participantCount = participantCount;
        this.totalFee = totalFee;
        this.note = note;
    }

    // Getter
    public Booking getBooking() {
        return booking;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public String getNote() {
        return note;
    }

    // Setter
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "BookingDetail{" +
                "booking=" + booking +
                ", participantCount=" + participantCount +
                ", totalFee=" + totalFee +
                ", note='" + note + '\'' +
                '}';
    }
}