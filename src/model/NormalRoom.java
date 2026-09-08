package model;

/**
 * Phòng học nhóm thông thường.
 */
public class NormalRoom extends Room {

    /**
     * Constructor mặc định.
     */
    public NormalRoom() {
        super();
    }

    /**
     * Constructor đầy đủ thông tin.
     */
    public NormalRoom(String roomId,
                      String roomName,
                      int floor,
                      int capacity,
                      String status) {

        super(roomId, roomName, floor, capacity, status);
    }

    /**
     * Phòng thường được miễn phí.
     */
    @Override
    public double calculateFee(int hours) {
        return 0;
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