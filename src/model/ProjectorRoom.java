package model;

public class ProjectorRoom extends Room {

    public ProjectorRoom() {
        super();
    }

    public ProjectorRoom(String roomId, String roomName, int floor,
                         int capacity, String status) {
        super(roomId, roomName, floor, capacity, status);
    }

    @Override
    public double calculateFee(int hours) {
        return hours * 20000;
    }
}
//lan2