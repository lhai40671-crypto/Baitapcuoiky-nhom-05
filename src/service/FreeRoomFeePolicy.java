package service;

import model.Booking;

public class FreeRoomFeePolicy implements RoomFeePolicy {

    @Override
    public double calculateFee(Booking booking) {
        return 0;
    }
}