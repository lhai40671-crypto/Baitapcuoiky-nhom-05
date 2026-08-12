package model;

public class BookingDetail {
    private Booking booking;
    private int participantCount;
    private double totalFee;
    private String note;

    public BookingDetail() {
    }

    public BookingDetail(Booking booking, int participantCount,
                         double totalFee, String note) {
        this.booking = booking;
        this.participantCount = participantCount;
        this.totalFee = totalFee;
        this.note = note;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getNote() {
        return note;
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