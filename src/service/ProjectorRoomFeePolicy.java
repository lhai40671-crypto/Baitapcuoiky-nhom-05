package service;

import model.Booking;

public class ProjectorRoomFeePolicy implements RoomFeePolicy {

    private static final double PRICE_PER_HOUR = 20000;

    @Override
    public double calculateFee(Booking booking) {

        long hours = booking.getTimeSlot().getHours();

        return hours * PRICE_PER_HOUR;
    }
}