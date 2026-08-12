package service;

import model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final List<Room> rooms;

    public RoomService() {
        rooms = new ArrayList<>();
    }

    // Thêm phòng
    public void addRoom(Room room) {
        if (room == null) {
            return;
        }

        if (findById(room.getRoomId()) != null) {
            return;
        }

        rooms.add(room);
    }

    // Tìm phòng theo mã
    public Room findById(String roomId) {
        if (roomId == null) {
            return null;
        }

        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }

        return null;
    }

    // Lấy tất cả phòng
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    // Lấy các phòng đang hoạt động
    public List<Room> getAvailableRooms() {
        List<Room> result = new ArrayList<>();

        for (Room room : rooms) {
            if (room.isAvailable()) {
                result.add(room);
            }
        }

        return result;
    }

    // Tìm phòng theo loại
    public List<Room> findByType(String type) {
        List<Room> result = new ArrayList<>();

        if (type == null) {
            return result;
        }

        for (Room room : rooms) {
            if (room.getClass().getSimpleName()
                    .equalsIgnoreCase(type)) {
                result.add(room);
            }
        }

        return result;
    }

    // Cập nhật trạng thái phòng
    public boolean updateStatus(String roomId, String status) {
        Room room = findById(roomId);

        if (room == null) {
            return false;
        }

        room.setStatus(status);
        return true;
    }
}