package model;

public class SeminarRoom extends Room {

    // Phí phòng seminar: 50.000đ/giờ
    private static final double FEE_PER_HOUR = 50000;

    // Constructor mặc định
    public SeminarRoom() {
        super();
    }

    // Constructor đầy đủ
    public SeminarRoom(String roomId,
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
        return "SeminarRoom{" +
                "roomId='" + getRoomId() + '\'' +
                ", roomName='" + getRoomName() + '\'' +
                ", floor=" + getFloor() +
                ", capacity=" + getCapacity() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}//