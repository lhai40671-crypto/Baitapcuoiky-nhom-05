package model;

public abstract class Room {

    private String roomId;
    private String roomName;
    private int floor;
    private int capacity;
    private String status;

    public Room() {
    }

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

    public abstract String getRoomType();

    public abstract double getPricePerHour();

    public boolean isAvailable() {
        return "Đang hoạt động".equals(status);
    }

    @Override
    public String toString() {
        return "Mã phòng: " + roomId
                + " | Tên: " + roomName
                + " | Tầng: " + floor
                + " | Sức chứa: " + capacity
                + " | Loại: " + getRoomType()
                + " | Trạng thái: " + status;
    }
}
// Update lan 3