package service;

import model.Room;
import repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private RoomRepository roomRepository;

    public RoomService() {
        roomRepository = new RoomRepository();
    }

    // Lấy tất cả phòng
    public List<Room> getAllRooms() {

        return roomRepository.getAll();
    }

    // Tìm phòng theo mã
    public Room findRoomById(String roomId) {

        return roomRepository.findById(roomId);
    }

    // Tìm phòng theo loại
    public List<Room> findRoomByType(
            String roomType) {

        return roomRepository.findByType(roomType);
    }

    // Lấy danh sách phòng đang hoạt động
    public List<Room> getAvailableRooms() {

        List<Room> result = new ArrayList<>();

        List<Room> rooms =
                roomRepository.getAll();

        for (Room room : rooms) {

            if (room.getStatus()
                    .equalsIgnoreCase("Dang hoat dong")) {

                result.add(room);
            }
        }

        return result;
    }

    // Kiểm tra phòng có thể đặt không
    public boolean isRoomAvailable(String roomId) {

        Room room =
                roomRepository.findById(roomId);

        if (room == null) {
            return false;
        }

        return room.getStatus()
                .equalsIgnoreCase("Dang hoat dong");
    }
}