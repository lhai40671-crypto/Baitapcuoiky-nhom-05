package model;

public class SeminarRoom extends Room {

    public SeminarRoom() {
    }

    public SeminarRoom(String roomId,
                       String roomName,
                       int floor,
                       int capacity,
                       String status) {

        super(roomId, roomName,
                floor, capacity, status);
    }

    @Override
    public String getRoomType() {
        return "SEMINAR";
    }

    @Override
    public double getPricePerHour() {
        return 50000;
    }
}
/ Update lan 3