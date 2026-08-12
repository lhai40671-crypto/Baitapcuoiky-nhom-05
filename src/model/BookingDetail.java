package model;

public class BookingDetail {

    private int numberOfPeople;
    private String purpose;
    private String note;

    public BookingDetail() {
    }

    public BookingDetail(int numberOfPeople,
                         String purpose,
                         String note) {

        this.numberOfPeople = numberOfPeople;
        this.purpose = purpose;
        this.note = note;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Số người: " + numberOfPeople
                + " | Mục đích: " + purpose
                + " | Ghi chú: " + note;
    }
}