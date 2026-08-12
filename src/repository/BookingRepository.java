package repository;

import model.Booking;
import model.Room;
import model.Student;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private final String fileName =
            "bookings.txt";

    public void save(
            List<Booking> bookings)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {

            for (Booking booking : bookings) {

                writer.write(
                        booking.getBookingId()
                                + "|"
                                + booking.getStudent()
                                .getStudentId()
                                + "|"
                                + booking.getRoom()
                                .getRoomId()
                                + "|"
                                + booking.getStartTime()
                                + "|"
                                + booking.getEndTime()
                                + "|"
                                + booking.getNumberOfPeople()
                                + "|"
                                + booking.getStatus());

                writer.newLine();
            }
        }
    }

    public List<Booking> load(
            List<Student> students,
            List<Room> rooms)
            throws IOException {

        List<Booking> bookings =
                new ArrayList<>();

        File file =
                new File(fileName);

        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] data =
                        line.split("\\|");

                if (data.length != 7) {
                    continue;
                }

                Student student =
                        findStudent(
                                students,
                                data[1]);

                Room room =
                        findRoom(
                                rooms,
                                data[2]);

                if (student == null
                        || room == null) {

                    continue;
                }

                LocalDateTime start =
                        LocalDateTime.parse(data[3]);

                LocalDateTime end =
                        LocalDateTime.parse(data[4]);

                int people =
                        Integer.parseInt(data[5]);

                Booking booking =
                        new Booking(
                                data[0],
                                student,
                                room,
                                start,
                                end,
                                people);

                booking.setStatus(data[6]);

                bookings.add(booking);

                student.addBooking(booking);
            }
        }

        return bookings;
    }

    private Student findStudent(
            List<Student> students,
            String id) {

        for (Student student : students) {

            if (student.getStudentId()
                    .equals(id)) {

                return student;
            }
        }

        return null;
    }

    private Room findRoom(
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
}