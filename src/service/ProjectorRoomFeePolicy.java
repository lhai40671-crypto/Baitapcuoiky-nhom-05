package service;

import model.Booking;

public class ProjectorRoomFeePolicy implements RoomFeePolicy {

    private static final double PRICE = 20000;

    @Override
    public double calculateFee(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException(
                    "Lịch đặt không tồn tại!");
        }

        return PRICE * booking.getHours();
    }
}