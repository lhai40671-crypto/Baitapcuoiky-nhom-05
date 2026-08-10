package model;

public class NormalRoom extends Room {

    public NormalRoom() {
        super();
    }

    public NormalRoom(String roomId, String roomName, int floor,
                      int capacity, String status) {
        super(roomId, roomName, floor, capacity, "Normal", status);
    }

    @Override
    public double calculatePrice() {
        return 0;
    }
}