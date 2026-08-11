package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot() {
    }

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // Kiểm tra thời gian có hợp lệ không
    public boolean isValid() {
        return startTime != null
                && endTime != null
                && endTime.isAfter(startTime);
    }

    // Kiểm tra hai khung giờ có bị trùng nhau không
    public boolean isOverlapping(TimeSlot other) {
        if (!this.date.equals(other.date)) {
            return false;
        }

        return startTime.isBefore(other.endTime)
                && endTime.isAfter(other.startTime);
    }

    // Tính số giờ sử dụng
    public long getHours() {
        return java.time.Duration.between(startTime, endTime).toHours();
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