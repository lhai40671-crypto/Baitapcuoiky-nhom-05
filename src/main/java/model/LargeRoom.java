package model;

public class LargeRoom extends Room {

    public LargeRoom() {
        super();
    }

    public LargeRoom(String roomId, String roomName, int capacity) {
        super(roomId, roomName, 1, capacity, "Phòng họp seminar", "Đang hoạt động");
    }

    @Override
    public double calculatePrice() {
        return 50000;
    }
}