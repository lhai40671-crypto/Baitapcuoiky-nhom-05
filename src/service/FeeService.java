package service;

import exception.BookingException;
import model.Booking;
import model.NormalRoom;
import model.ProjectorRoom;
import model.SeminarRoom;
import model.Room;

public class FeeService {

    public double calculateFee(Booking booking) {

        if (booking == null) {
            throw new BookingException("Booking khong duoc null!");
        }

        Room room = booking.getRoom();
        RoomFeePolicy policy = resolvePolicy(room);

        return policy.calculateFee(booking);
    }

    private RoomFeePolicy resolvePolicy(Room room) {

        if (room instanceof ProjectorRoom) {
            return new ProjectorRoomFeePolicy();
        }

        if (room instanceof SeminarRoom) {
            return new SeminarRoomFeePolicy();
        }

        if (room instanceof NormalRoom) {
            return new FreeRoomFeePolicy();
        }

        throw new BookingException("Khong xac dinh duoc loai phong!");
    }
}