package repository;

import model.NormalRoom;
import model.ProjectorRoom;
import model.Room;
import model.SeminarRoom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private static final String FILE_NAME = "rooms.txt";

    // Đọc tất cả phòng
    public List<Room> getAll() {

        List<Room> rooms = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return rooms;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 6) {
                    continue;
                }

                String roomId = data[0];
                String roomName = data[1];
                int floor = Integer.parseInt(data[2]);
                int capacity = Integer.parseInt(data[3]);
                String roomType = data[4];
                String status = data[5];

                Room room = null;

                switch (roomType.toLowerCase()) {

                    case "normal":
                        room = new NormalRoom(
                                roomId,
                                roomName,
                                floor,
                                capacity,
                                status
                        );
                        break;

                    case "projector":
                        room = new ProjectorRoom(
                                roomId,
                                roomName,
                                floor,
                                capacity,
                                status
                        );
                        break;

                    case "seminar":
                        room = new SeminarRoom(
                                roomId,
                                roomName,
                                floor,
                                capacity,
                                status
                        );
                        break;
                }

                if (room != null) {
                    rooms.add(room);
                }
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Loi doc file phong: "
                            + e.getMessage()
            );
        }

        return rooms;
    }

    // Tìm phòng theo mã
    public Room findById(String roomId) {

        List<Room> rooms = getAll();

        for (Room room : rooms) {

            if (room.getRoomId()
                    .equalsIgnoreCase(roomId)) {

                return room;
            }
        }

        return null;
    }

    // Tìm phòng theo loại
    public List<Room> findByType(String roomType) {

        List<Room> result = new ArrayList<>();

        List<Room> rooms = getAll();

        for (Room room : rooms) {

            if (room.getRoomType()
                    .equalsIgnoreCase(roomType)) {

                result.add(room);
            }
        }

        return result;
    }

    // Thêm phòng
    public void save(Room room) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true))) {

            writer.write(
                    room.getRoomId() + "|" +
                            room.getRoomName() + "|" +
                            room.getFloor() + "|" +
                            room.getCapacity() + "|" +
                            room.getRoomType() + "|" +
                            room.getStatus()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Loi ghi file phong: "
                            + e.getMessage()
            );
        }
    }
}
////