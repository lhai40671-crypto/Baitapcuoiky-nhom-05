package model;

/**
 * Phòng học nhóm có máy chiếu.
 */
public class ProjectorRoom extends Room {

    private static final double FEE_PER_HOUR = 20000;

    /**
     * Constructor mặc định.
     */
    public ProjectorRoom() {
        super();
    }

    /**
     * Constructor đầy đủ thông tin.
     */
    public ProjectorRoom(String roomId,
                         String roomName,
                         int floor,
                         int capacity,
                         String status) {

        super(roomId, roomName, floor, capacity, status);
    }

    /**
     * Tính phí sử dụng phòng máy chiếu.
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
        return "ProjectorRoom{" +
                "roomId='" + getRoomId() + '\'' +
                ", roomName='" + getRoomName() + '\'' +
                ", floor=" + getFloor() +
                ", capacity=" + getCapacity() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}