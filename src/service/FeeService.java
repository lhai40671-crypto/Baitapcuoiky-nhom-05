package service;

import fee.FreeRoomFeePolicy;
import fee.ProjectorRoomFeePolicy;
import fee.RoomFeePolicy;
import fee.SeminarRoomFeePolicy;
import model.Room;

public class FeeService {

    public double calculateFee(
            Room room,
            double hours) {

        if (room == null) {

            throw new IllegalArgumentException(
                    "Phong khong ton tai!"
            );
        }

        if (hours <= 0) {

            throw new IllegalArgumentException(
                    "So gio phai lon hon 0!"
            );
        }

        RoomFeePolicy policy =
                getPolicy(room);

        return policy.calculateFee(hours);
    }

    private RoomFeePolicy getPolicy(
            Room room) {

        switch (
                room.getRoomType()
                        .toLowerCase()
        ) {

            case "normal":

                return new FreeRoomFeePolicy();

            case "projector":

                return new ProjectorRoomFeePolicy();

            case "seminar":

                return new SeminarRoomFeePolicy();

            default:

                throw new IllegalArgumentException(
                        "Loai phong khong duoc ho tro!"
                );
        }
    }
}