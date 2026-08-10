package model;

public class ProjectorRoom extends Room {

    public ProjectorRoom() {
        super();
    }

    public ProjectorRoom(String roomId, String roomName, int floor,
                         int capacity, String status) {
        super(roomId, roomName, floor, capacity, "Projector", status);
    }

    @Override
    public double calculatePrice() {
        return 20000;
    }
}