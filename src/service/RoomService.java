package service;

import exception.BookingException;
import model.Room;
import repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.getAll();
    }

    public Room findById(String roomId) {
        return roomRepository.findById(roomId);
    }

    public List<Room> findByType(String type) {
        return roomRepository.findByType(type);
    }

    public List<Room> getActiveRooms() {

        List<Room> result = new ArrayList<>();

        for (Room room : roomRepository.getAll()) {
            if (room.isAvailable()) {
                result.add(room);
            }
        }

        return result;
    }

    public void addRoom(Room room) {

        if (room == null) {
            throw new BookingException("Phong khong duoc null!");
        }

        if (findById(room.getRoomId()) != null) {
            throw new BookingException("Ma phong da ton tai!");
        }

        roomRepository.save(room);
    }

    public void removeRoom(String roomId) {

        boolean removed = roomRepository.remove(roomId);

        if (!removed) {
            throw new BookingException("Khong tim thay phong!");
        }
    }
}