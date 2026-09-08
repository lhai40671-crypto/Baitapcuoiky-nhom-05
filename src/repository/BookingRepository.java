BookingRepository

        package repository;

import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private static final String FILE_NAME = "data/bookings.txt";

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;

    public BookingRepository(StudentRepository studentRepository,
                             RoomRepository roomRepository) {
        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
    }

    // Đọc toàn bộ danh sách đặt phòng từ file
    public List<Booking> getAll() {

        List<Booking> bookings = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                // bookingId|studentId|roomId|date|startTime|endTime|participantCount|status
                if (data.length != 😎 {
                    continue;
                }

                Booking booking = buildBooking(data);

                if (booking != null) {
                    bookings.add(booking);
                }
            }

        } catch (IOException e) {
            System.out.println("Loi doc file dat phong: " + e.getMessage());
        }

        return bookings;
    }

    // Dựng lại object Booking từ dòng dữ liệu text
    private Booking buildBooking(String[] data) {

        try {

            String bookingId = data[0];
            String studentId = data[1];
            String roomId = data[2];
            LocalDate date = LocalDate.parse(data[3]);
            LocalTime startTime = LocalTime.parse(data[4]);
            LocalTime endTime = LocalTime.parse(data[5]);
            int participantCount = Integer.parseInt(data[6]);
            String status = data[7];

            Student student = studentRepository.findById(studentId);
            Room room = roomRepository.findById(roomId);

            if (student == null || room == null) {
                System.out.println(
                        "Bo qua booking " + bookingId
                                + ": khong tim thay sinh vien hoac phong");
                return null;
            }

            TimeSlot timeSlot = new TimeSlot(date, startTime, endTime);

            return new Booking(bookingId, student, room, timeSlot, participantCount, status);

        } catch (Exception e) {
            System.out.println("Loi doc dong du lieu booking: " + e.getMessage());
            return null;
        }
    }

    public Booking findById(String bookingId) {

        for (Booking booking : getAll()) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }

        return null;
    }

    public List<Booking> findByStudentId(String studentId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : getAll()) {
            if (booking.getStudent() != null
                    && booking.getStudent().getUserId().equalsIgnoreCase(studentId)) {
                result.add(booking);
            }
        }

        return result;
    }

    public List<Booking> findByRoomId(String roomId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : getAll()) {
            if (booking.getRoom() != null
                    && booking.getRoom().getRoomId().equalsIgnoreCase(roomId)) {
                result.add(booking);
            }
        }

        return result;
    }

    // Ghi thêm 1 booking mới vào cuối file
    public void save(Booking booking) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            writer.write(toLine(booking));
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Loi ghi file dat phong: " + e.getMessage());
        }
    }

    // Ghi lại toàn bộ danh sách (dùng khi update/cancel)
    public void saveAll(List<Booking> bookings) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Booking booking : bookings) {
                writer.write(toLine(booking));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Loi ghi file dat phong: " + e.getMessage());
        }
    }

    private String toLine(Booking booking) {

        TimeSlot timeSlot = booking.getTimeSlot();

        return booking.getBookingId() + "|" +
                booking.getStudent().getUserId() + "|" +
                booking.getRoom().getRoomId() + "|" +
                timeSlot.getDate() + "|" +
                timeSlot.getStartTime() + "|" +
                timeSlot.getEndTime() + "|" +
                booking.getParticipantCount() + "|" +
                booking.getStatus();
    }

    public boolean cancel(String bookingId) {

        List<Booking> bookings = getAll();

        boolean found = false;

        for (Booking booking : bookings) {

            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                booking.setStatus("Đã hủy");
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