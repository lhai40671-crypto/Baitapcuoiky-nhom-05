package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // Constructor mặc định
    public TimeSlot() {
    }

    // Constructor đầy đủ
    public TimeSlot(LocalDate date,
                    LocalTime startTime,
                    LocalTime endTime) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter
    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    // Setter
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // Kiểm tra khung giờ có hợp lệ không
    public boolean isValid() {

        if (date == null) {
            return false;
        }

        if (startTime == null || endTime == null) {
            return false;
        }

        return endTime.isAfter(startTime);
    }

    // Kiểm tra hai khung giờ có bị trùng nhau không
    public boolean isOverlapping(TimeSlot other) {

        if (other == null) {
            return false;
        }

        if (date == null || other.date == null) {
            return false;
        }

        // Khác ngày thì không trùng
        if (!date.equals(other.date)) {
            return false;
        }

        if (startTime == null || endTime == null
                || other.startTime == null || other.endTime == null) {
            return false;
        }

        return startTime.isBefore(other.endTime)
                && endTime.isAfter(other.startTime);
    }

    // Tính số giờ sử dụng
    public long getHours() {

        if (!isValid()) {
            return 0;
        }

        return Duration.between(startTime, endTime).toHours();
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}