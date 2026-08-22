package service;

import model.Booking;
import model.Room;
import model.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {

    private final List<Booking> bookings;
    private final List<Student> students;
    private final List<Room> rooms;

    private final BookingValidator validator;

    public BookingService(
            List<Booking> bookings,
            List<Student> students,
            List<Room> rooms) {

        this.bookings = bookings;
        this.students = students;
        this.rooms = rooms;

        this.validator = new BookingValidator();
    }

    public Booking createBooking(
            String studentId,
            String roomId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int numberOfPeople) {

        Student student = findStudent(studentId);
        Room room = findRoom(roomId);

        validator.validateStudent(student);
        validator.validateRoom(room);
        validator.validateTime(startTime, endTime);
        validator.validateCapacity(room, numberOfPeople);

        validator.validateOverlap(
                room,
                startTime,
                endTime,
                bookings
        );

        validator.validateDailyLimit(
                student,
                startTime,
                endTime,
                bookings
        );

        String bookingId =
                "B" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        Booking booking = new Booking(
                bookingId,
                student,
                room,
                startTime,
                endTime,
                numberOfPeople,
                "Đã đặt"
        );

        bookings.add(booking);

        System.out.println(
                "Dat phong thanh cong! Ma dat phong: "
                        + bookingId
        );

        return booking;
    }

    public void cancelBooking(
            String bookingId,
            String studentId) {

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Ma dat phong khong ton tai!"
            );
        }

        if (booking.getStatus().equals("Đã hủy")) {
            throw new IllegalArgumentException(
                    "Lich dat nay da bi huy truoc do!"
            );
        }

        if (!booking.getStudent()
                .getStudentId()
                .equals(studentId)) {

            throw new IllegalArgumentException(
                    "Ban khong phai nguoi tao lich dat nay!"
            );
        }

        booking.setStatus("Đã hủy");

        System.out.println(
                "Huy lich dat phong thanh cong!"
        );
    }

    public List<Booking> getBookingsByStudent(
            String studentId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {

            if (booking.getStudent()
                    .getStudentId()
                    .equals(studentId)) {

                result.add(booking);
            }
        }

        return result;
    }

    public List<Booking> getBookingsByRoom(
            String roomId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {

            if (booking.getRoom()
                    .getRoomId()
                    .equals(roomId)) {

                result.add(booking);
            }
        }

        return result;
    }

    public Booking findBooking(String bookingId) {

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equals(bookingId)) {

                return booking;
            }
        }

        return null;
    }

    private Student findStudent(String studentId) {

        for (Student student : students) {

            if (student.getStudentId()
                    .equals(studentId)) {

                return student;
            }
        }

        return null;
    }

    private Room findRoom(String roomId) {

        for (Room room : rooms) {

            if (room.getRoomId()
                    .equals(roomId)) {

                return room;
            }
        }

        return null;
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}