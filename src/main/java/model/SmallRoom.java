package model;

public class SmallRoom extends Room {

    public SmallRoom() {
        super();
    }

    public SmallRoom(String roomId, String roomName, int capacity) {
        super(roomId, roomName, 1, capacity, "Phòng thường", "Đang hoạt động");
    }

    @Override
    public double calculatePrice() {
        return 0;
    }
}