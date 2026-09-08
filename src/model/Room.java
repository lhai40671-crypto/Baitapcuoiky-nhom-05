package model;

/**
 * Lớp trừu tượng Room đại diện cho phòng học nhóm.
 *
 * Các loại phòng cụ thể:
 * - NormalRoom
 * - ProjectorRoom
 * - SeminarRoom
 */
public abstract class Room {

    private String roomId;
    private String roomName;
    private int floor;
    private int capacity;
    private String status;

    /**
     * Constructor mặc định.
     */
    public Room() {
    }

    /**
     * Constructor đầy đủ thông tin phòng.
     */
    public Room(String roomId,
                String roomName,
                int floor,
                int capacity,
                String status) {

        this.roomId = roomId;
        this.roomName = roomName;
        this.floor = floor;
        this.capacity = capacity;
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Kiểm tra phòng có đang hoạt động hay không.
     *
     * Việc không cho đặt phòng bảo trì
     * sẽ được xử lý ở BookingValidator/Service.
     */
    public boolean isActive() {

        if (status == null) {
            return false;
        }

        return status.equalsIgnoreCase("Đang hoạt động")
                || status.equalsIgnoreCase("Active");
    }

    /**
     * Phương thức trừu tượng tính phí phòng.
     *
     * Các lớp con sẽ override phương thức này.
     */
    public abstract double calculateFee(int hours);

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomName='" + roomName + '\'' +
                ", floor=" + floor +
                ", capacity=" + capacity +
                ", status='" + status + '\'' +
                '}';
    }
}