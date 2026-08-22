package service;

import model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final List<Room> rooms;

    public RoomService(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room findById(String roomId) {

        for (Room room : rooms) {

            if (room.getRoomId()
                    .equalsIgnoreCase(roomId)) {

                return room;
            }
        }

        return null;
    }

    public List<Room> findByType(String type) {

        List<Room> result = new ArrayList<>();

        for (Room room : rooms) {

            if (room.getRoomType()
                    .equalsIgnoreCase(type)) {

                result.add(room);
            }
        }

        return result;
    }

    public List<Room> getActiveRooms() {

        List<Room> result = new ArrayList<>();

        for (Room room : rooms) {

            if (room.isActive()) {
                result.add(room);
            }
        }

        return result;
    }

    public void addRoom(Room room) {

        if (room == null) {
            throw new IllegalArgumentException(
                    "Phong khong duoc null!"
            );
        }

        if (findById(room.getRoomId()) != null) {
            throw new IllegalArgumentException(
                    "Ma phong da ton tai!"
            );
        }

        rooms.add(room);
    }

    public void removeRoom(String roomId) {

        Room room = findById(roomId);

        if (room == null) {
            throw new IllegalArgumentException(
                    "Khong tim thay phong!"
            );
        }

        rooms.remove(room);
    }
}