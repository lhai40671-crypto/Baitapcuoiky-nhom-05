package model;

public class NormalRoom extends Room {

    // Phí thuê phòng thường theo mỗi giờ
    private static final double FEE_PER_HOUR = 10000;

    // Constructor mặc định
    public NormalRoom() {
        super();
    }

    // Constructor đầy đủ
    public NormalRoom(String roomId, String roomName, int floor,
                      int capacity, String status) {
        super(roomId, roomName, floor, capacity, status);
    }

    // Tính phí thuê phòng theo số giờ
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
//
