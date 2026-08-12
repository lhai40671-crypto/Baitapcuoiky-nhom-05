package repository;

import model.NormalRoom;
import model.ProjectorRoom;
import model.Room;
import model.SeminarRoom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final String fileName =
            "rooms.txt";

    public void save(List<Room> rooms)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {

            for (Room room : rooms) {

                writer.write(
                        room.getRoomId()
                                + "|"
                                + room.getRoomName()
                                + "|"
                                + room.getFloor()
                                + "|"
                                + room.getCapacity()
                                + "|"
                                + room.getRoomType()
                                + "|"
                                + room.getStatus());

                writer.newLine();
            }
        }
    }

    public List<Room> load()
            throws IOException {

        List<Room> rooms =
                new ArrayList<>();

        File file =
                new File(fileName);

        if (!file.exists()) {
            return rooms;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] data =
                        line.split("\\|");

                if (data.length != 6) {
                    continue;
                }

                String id = data[0];
                String name = data[1];
                int floor =
                        Integer.parseInt(data[2]);
                int capacity =
                        Integer.parseInt(data[3]);
                String type = data[4];
                String status = data[5];

                Room room;

                switch (type) {

                    case "NORMAL":
                        room =
                                new NormalRoom(
                                        id, name,
                                        floor,
                                        capacity,
                                        status);
                        break;

                    case "PROJECTOR":
                        room =
                                new ProjectorRoom(
                                        id, name,
                                        floor,
                                        capacity,
                                        status);
                        break;

                    case "SEMINAR":
                        room =
                                new SeminarRoom(
                                        id, name,
                                        floor,
                                        capacity,
                                        status);
                        break;

                    default:
                        continue;
                }

                rooms.add(room);
            }
        }

        return rooms;
    }

    public Room findById(
            List<Room> rooms,
            String id) {

        for (Room room : rooms) {

            if (room.getRoomId()
                    .equals(id)) {

                return room;
            }
        }

        return null;
    }

    public List<Room> findByType(
            List<Room> rooms,
            String type) {

        List<Room> result =
                new ArrayList<>();

        for (Room room : rooms) {

            if (room.getRoomType()
                    .equalsIgnoreCase(type)) {

                result.add(room);
            }
        }

        return result;
    }
}