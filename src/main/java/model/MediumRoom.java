package model;

public class MediumRoom extends Room {

    public MediumRoom() {
        super();
    }

    public MediumRoom(String roomId, String roomName, int capacity) {
        super(roomId, roomName, 1, capacity, "Phòng có máy chiếu", "Đang hoạt động");
    }

    @Override
    public double calculatePrice() {
        return 20000;
    }
}