package service;

import model.Booking;
import model.ProjectorRoom;
import model.SeminarRoom;
import model.Room;

public class FeeService {

    public double calculateFee(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking khong duoc null!"
            );
        }

        Room room = booking.getRoom();

        if (room instanceof ProjectorRoom) {
            RoomFeePolicy policy = new ProjectorRoomFeePolicy();
            return policy.calculateFee(booking);
        }

        if (room instanceof SeminarRoom) {
            RoomFeePolicy policy = new SeminarRoomFeePolicy();
            return policy.calculateFee(booking);
        }

        return 0;
    }
}