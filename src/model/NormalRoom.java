package model;

public class NormalRoom extends Room {

    public NormalRoom() {
    }

    public NormalRoom(String roomId,
                      String roomName,
                      int floor,
                      int capacity,
                      String status) {

        super(roomId, roomName,
                floor, capacity, status);
    }

    @Override
    public String getRoomType() {
        return "NORMAL";
    }

    @Override
    public double getPricePerHour() {
        return 0;
    }
}
// Update lan 3