package service;

import model.NormalRoom;
import model.ProjectorRoom;
import model.Room;
import model.SeminarRoom;

public class FeeService {

    public double calculateFee(Room room, double hours) {
        if (room == null || hours <= 0) {
            return 0;
        }

        if (room instanceof NormalRoom) {
            return 0;
        }

        if (room instanceof ProjectorRoom) {
            return hours * 20000;
        }

        if (room instanceof SeminarRoom) {
            return hours * 50000;
        }

        return 0;
    }
}