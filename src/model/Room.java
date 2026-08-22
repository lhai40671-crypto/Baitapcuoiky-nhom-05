package model;

public abstract class Room {

    private String roomId;
    private String roomName;
    private int floor;
    private int capacity;
    private String status;

    // Constructor mặc định
    public Room() {
    }

    // Constructor đầy đủ
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

    // Getter
    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getFloor() {
        return floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Kiểm tra phòng có đang hoạt động hay không
    public boolean isAvailable() {
        return status != null
                && status.equalsIgnoreCase("Đang hoạt động");
    }

    // Mỗi loại phòng có cách tính phí khác nhau
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
}//