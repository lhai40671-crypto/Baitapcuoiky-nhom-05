package repository;

import model.Booking;
import model.Room;
import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private final String fileName = "bookings.txt";

    public void save(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (Booking b : bookings) {
                writer.write(
                        b.getBookingId() + "|" +
                                b.getStudent().getId() + "|" +
                                b.getRoom().getRoomId() + "|" +
                                b.getDate() + "|" +
                                b.getStartTime() + "|" +
                                b.getEndTime() + "|" +
                                b.getNumberOfPeople() + "|" +
                                b.getStatus()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    public List<Booking> load(
            List<Student> students,
            List<Room> rooms) {

        List<Booking> bookings = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                Student student = null;
                Room room = null;

                for (Student s : students) {
                    if (s.getId().equals(data[1])) {
                        student = s;
                        break;
                    }
                }

                for (Room r : rooms) {
                    if (r.getRoomId().equals(data[2])) {
                        room = r;
                        break;
                    }
                }

                if (student != null && room != null) {

                    Booking booking = new Booking(
                            data[0],
                            student,
                            room,
                            data[3],
                            data[4],
                            data[5],
                            Integer.parseInt(data[6])
                    );

                    booking.setStatus(data[7]);

                    bookings.add(booking);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Chưa có file bookings.txt");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }

        return bookings;
    }

    public List<String> read() {
        List<String> data = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

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