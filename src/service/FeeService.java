package service;

import model.Booking;

public class FeeService {

    public double calculateFee(
            Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Lịch đặt không tồn tại!");
        }

        RoomFeePolicy policy;

        switch (booking.getRoom()
                .getRoomType()) {

            case "NORMAL":
                policy =
                        new FreeRoomFeePolicy();
                break;

            case "PROJECTOR":
                policy =
                        new ProjectorRoomFeePolicy();
                break;

            case "SEMINAR":
                policy =
                        new SeminarRoomFeePolicy();
                break;

            default:
                throw new IllegalArgumentException(
                        "Loại phòng không hợp lệ!");
        }

        return policy.calculateFee(booking);
    }
}