package repository;

import model.Booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private static final String FILE_NAME = "bookings.txt";


    public List<Booking> getAll() {

        List<Booking> bookings = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 8) {
                    continue;
                }

                Booking booking = new Booking(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        Integer.parseInt(data[6]),
                        data[7]
                );

                bookings.add(booking);
            }

        } catch (IOException | NumberFormatException e) {

            System.out.println(
                    "Loi doc file dat phong: "
                            + e.getMessage()
            );
        }

        return bookings;
    }


    public Booking findById(String bookingId) {

        List<Booking> bookings = getAll();

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                return booking;
            }
        }

        return null;
    }


    public List<Booking> findByStudentId(
            String studentId) {

        List<Booking> result = new ArrayList<>();

        List<Booking> bookings = getAll();

        for (Booking booking : bookings) {

            if (booking.getStudentId()
                    .equalsIgnoreCase(studentId)) {

                result.add(booking);
            }
        }

        return result;
    }


    public List<Booking> findByRoomId(
            String roomId) {

        List<Booking> result = new ArrayList<>();

        List<Booking> bookings = getAll();

        for (Booking booking : bookings) {

            if (booking.getRoomId()
                    .equalsIgnoreCase(roomId)) {

                result.add(booking);
            }
        }

        return result;
    }


    public void save(Booking booking) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true))) {

            writer.write(
                    booking.getBookingId() + "|" +
                            booking.getStudentId() + "|" +
                            booking.getRoomId() + "|" +
                            booking.getDate() + "|" +
                            booking.getStartTime() + "|" +
                            booking.getEndTime() + "|" +
                            booking.getNumberOfPeople() + "|" +
                            booking.getStatus()
            );

            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Loi ghi file dat phong: "
                            + e.getMessage()
            );
        }
    }

    // Ghi lại toàn bộ danh sách
    public void saveAll(List<Booking> bookings) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME))) {

            for (Booking booking : bookings) {

                writer.write(
                        booking.getBookingId() + "|" +
                                booking.getStudentId() + "|" +
                                booking.getRoomId() + "|" +
                                booking.getDate() + "|" +
                                booking.getStartTime() + "|" +
                                booking.getEndTime() + "|" +
                                booking.getNumberOfPeople() + "|" +
                                booking.getStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Loi ghi file dat phong: "
                            + e.getMessage()
            );
        }
    }


    public boolean cancel(String bookingId) {

        List<Booking> bookings = getAll();

        boolean found = false;

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                booking.setStatus("Da huy");

                found = true;

                break;
            }
        }

        if (found) {
            saveAll(bookings);
        }

        return found;
    }
}
////