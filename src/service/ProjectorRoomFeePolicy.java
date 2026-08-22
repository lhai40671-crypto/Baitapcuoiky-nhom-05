package service;

import model.Booking;

import java.time.Duration;

public class ProjectorRoomFeePolicy implements RoomFeePolicy {

    private static final double PRICE_PER_HOUR = 20000;

    @Override
    public double calculateFee(Booking booking) {

        long minutes = Duration.between(
                booking.getStartTime(),
                booking.getEndTime()
        ).toMinutes();

        double hours = minutes / 60.0;

        return hours * PRICE_PER_HOUR;
    }
}