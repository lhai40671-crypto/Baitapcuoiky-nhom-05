package model;

public class NormalRoom extends Room {

    // Phòng thường được miễn phí
    private static final double FEE_PER_HOUR = 0;

    // Constructor mặc định
    public NormalRoom() {
        super();
    }

    // Constructor đầy đủ
    public NormalRoom(String roomId,
                      String roomName,
                      int floor,
                      int capacity,
                      String status) {

        super(roomId, roomName, floor, capacity, status);
    }

    // Tính phí thuê phòng
    @Override
    public double calculateFee(int hours) {

        if (hours <= 0) {
            return 0;
        }

        return hours * FEE_PER_HOUR;
    }

    @Override
    public String toString() {
        return "NormalRoom{" +
                "roomId='" + getRoomId() + '\'' +
                ", roomName='" + getRoomName() + '\'' +
                ", floor=" + getFloor() +
                ", capacity=" + getCapacity() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}