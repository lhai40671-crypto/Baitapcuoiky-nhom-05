

        package repository;

import model.NormalRoom;
import model.ProjectorRoom;
import model.Room;
import model.SeminarRoom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private static final String FILE_NAME = "data/rooms.txt";

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

                Room room = createRoom(roomType, roomId, roomName, floor, capacity, status);

                if (room != null) {
                    rooms.add(room);
                }
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println("Loi doc file phong: " + e.getMessage());
        }

        return rooms;
    }

    // Tạo đúng loại phòng dựa theo chuỗi roomType đọc từ file
    private Room createRoom(String roomType,
                            String roomId,
                            String roomName,
                            int floor,
                            int capacity,
                            String status) {

        switch (roomType.toLowerCase()) {

            case "normal":
                return new NormalRoom(roomId, roomName, floor, capacity, status);

            case "projector":
                return new ProjectorRoom(roomId, roomName, floor, capacity, status);

            case "seminar":
                return new SeminarRoom(roomId, roomName, floor, capacity, status);

            default:
                System.out.println("Loai phong khong hop le: " + roomType);
                return null;
        }
    }

    // Suy ra chuỗi loại phòng từ kiểu class thực tế (vì Room không có field roomType)
    private String resolveRoomType(Room room) {

        if (room instanceof NormalRoom) {
            return "normal";
        }

        if (room instanceof ProjectorRoom) {
            return "projector";
        }

        if (room instanceof SeminarRoom) {
            return "seminar";
        }

        return "unknown";
    }

    // Tìm phòng theo mã
    public Room findById(String roomId) {

        for (Room room : getAll()) {
            if (room.getRoomId().equalsIgnoreCase(roomId)) {
                return room;
            }
        }

        return null;
    }

    // Tìm phòng theo loại
    public List<Room> findByType(String roomType) {

        List<Room> result = new ArrayList<>();

        for (Room room : getAll()) {
            if (resolveRoomType(room).equalsIgnoreCase(roomType)) {
                result.add(room);
            }
        }

        return result;
    }

    // Thêm phòng
    public void save(Room room) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            writer.write(
                    room.getRoomId() + "|" +
                            room.getRoomName() + "|" +
                            room.getFloor() + "|" +
                            room.getCapacity() + "|" +
                            resolveRoomType(room) + "|" +
                            room.getStatus()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println("Loi ghi file phong: " + e.getMessage());
        }
    }

    // Ghi lại toàn bộ danh sách phòng (dùng khi xóa/cập nhật)
    public void saveAll(List<Room> rooms) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Room room : rooms) {

                writer.write(
                        room.getRoomId() + "|" +
                                room.getRoomName() + "|" +
                                room.getFloor() + "|" +
                                room.getCapacity() + "|" +
                                resolveRoomType(room) + "|" +
                                room.getStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Loi ghi file phong: " + e.getMessage());
        }
    }

    // Xóa phòng theo mã
    public boolean remove(String roomId) {

        List<Room> rooms = getAll();

        boolean removed = rooms.removeIf(
                room -> room.getRoomId().equalsIgnoreCase(roomId));

        if (removed) {
            saveAll(rooms);
        }

        return removed;
    }
}
