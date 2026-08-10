package repository;

import model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final String fileName = "rooms.txt";

    public void save(List<Room> rooms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (Room room : rooms) {
                writer.write(
                        room.getRoomId() + "|" +
                                room.getRoomName() + "|" +
                                room.getFloor() + "|" +
                                room.getCapacity() + "|" +
                                room.getType() + "|" +
                                room.getStatus()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    public List<String> read() {
        List<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line);
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }

        return data;
    }
}