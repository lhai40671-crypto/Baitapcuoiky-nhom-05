package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeSlot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot() {
    }

    public TimeSlot(LocalDateTime startTime,
                    LocalDateTime endTime) {

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getHours() {

        if (startTime == null || endTime == null) {
            return 0;
        }

        long minutes = Duration.between(
                startTime, endTime).toMinutes();

        return minutes / 60.0;
    }

    public boolean isValid() {
        return startTime != null
                && endTime != null
                && endTime.isAfter(startTime);
    }

    public boolean overlaps(TimeSlot other) {

        if (other == null) {
            return false;
        }

        return startTime.isBefore(other.endTime)
                && endTime.isAfter(other.startTime);
    }

    @Override
    public String toString() {
        return startTime + " -> " + endTime
                + " (" + getHours() + " giờ)";
    }
}