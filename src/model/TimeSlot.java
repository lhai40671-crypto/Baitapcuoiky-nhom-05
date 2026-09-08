package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Đại diện cho một khung thời gian đặt phòng.
 */
public class TimeSlot {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * Constructor mặc định.
     */
    public TimeSlot() {
    }

    /**
     * Constructor đầy đủ.
     */
    public TimeSlot(LocalDate date,
                    LocalTime startTime,
                    LocalTime endTime) {

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

    /**
     * Kiểm tra khung thời gian có hợp lệ.
     *
     * Điều kiện:
     * - Ngày không được null.
     * - Giờ bắt đầu không được null.
     * - Giờ kết thúc không được null.
     * - Giờ kết thúc phải lớn hơn giờ bắt đầu.
     */
    public boolean isValid() {

        if (date == null) {
            return false;
        }

        if (startTime == null || endTime == null) {
            return false;
        }

        return endTime.isAfter(startTime);
    }

    /**
     * Kiểm tra hai khung thời gian có bị trùng nhau hay không.
     *
     * Hai khung:
     * 08:00 - 10:00
     * 09:00 - 11:00
     *
     * => Bị trùng.
     *
     * Hai khung:
     * 08:00 - 10:00
     * 10:00 - 12:00
     *
     * => Không bị trùng.
     */
    public boolean isOverlapping(TimeSlot other) {

        if (other == null) {
            return false;
        }

        if (!this.isValid() || !other.isValid()) {
            return false;
        }

        // Khác ngày thì không trùng.
        if (!this.date.equals(other.date)) {
            return false;
        }

        return this.startTime.isBefore(other.endTime)
                && this.endTime.isAfter(other.startTime);
    }

    /**
     * Tính thời lượng theo phút.
     */
    public long getDurationMinutes() {

        if (!isValid()) {
            return 0;
        }

        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * Tính thời lượng theo giờ.
     */
    public long getDurationHours() {

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