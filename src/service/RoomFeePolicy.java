package service;

import model.Booking;

public interface RoomFeePolicy {

    double calculateFee(Booking booking);
}