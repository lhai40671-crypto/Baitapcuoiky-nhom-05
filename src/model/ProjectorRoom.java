package model;

public class ProjectorRoom extends Room {

    public ProjectorRoom() {
    }

    public ProjectorRoom(String roomId,
                         String roomName,
                         int floor,
                         int capacity,
                         String status) {

        super(roomId, roomName,
                floor, capacity, status);
    }

    @Override
    public String getRoomType() {
        return "PROJECTOR";
    }

    @Override
    public double getPricePerHour() {
        return 20000;
    }
}