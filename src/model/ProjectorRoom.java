package model;

public class ProjectorRoom extends Room {

    // Phí phòng máy chiếu: 20.000đ/giờ
    private static final double FEE_PER_HOUR = 20000;

    // Constructor mặc định
    public ProjectorRoom() {
        super();
    }

    // Constructor đầy đủ
    public ProjectorRoom(String roomId,
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
        return "ProjectorRoom{" +
                "roomId='" + getRoomId() + '\'' +
                ", roomName='" + getRoomName() + '\'' +
                ", floor=" + getFloor() +
                ", capacity=" + getCapacity() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}