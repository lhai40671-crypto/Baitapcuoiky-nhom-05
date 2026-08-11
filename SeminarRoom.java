package model;

public class SeminarRoom extends Room {

    public SeminarRoom() {
        super();
    }

    public SeminarRoom(String roomId, String roomName, int floor,
                       int capacity, String status) {
        super(roomId, roomName, floor, capacity, status);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * 50000;
    }
}