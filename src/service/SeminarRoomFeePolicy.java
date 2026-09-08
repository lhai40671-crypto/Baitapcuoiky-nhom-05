package service;

import model.Booking;

public class SeminarRoomFeePolicy implements RoomFeePolicy {

    private static final double PRICE_PER_HOUR = 50000;

    @Override
    public double calculateFee(Booking booking) {

        long hours = booking.getTimeSlot().getHours();

        return hours * PRICE_PER_HOUR;
    }
}
