package model;

/**
 * Phòng họp seminar.
 */
public class SeminarRoom extends Room {

    private static final double FEE_PER_HOUR = 50000;

    /**
     * Constructor mặc định.
     */
    public SeminarRoom() {
        super();
    }

    /**
     * Constructor đầy đủ thông tin.
     */
    public SeminarRoom(String roomId,
                       String roomName,
                       int floor,
                       int capacity,
                       String status) {

        super(roomId, roomName, floor, capacity, status);
    }

    /**
     * Tính phí sử dụng phòng seminar.
     */
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
}