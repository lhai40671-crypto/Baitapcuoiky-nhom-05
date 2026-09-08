package service;

import exception.BookingException;
import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;
import repository.BookingRepository;
import repository.RoomRepository;
import repository.StudentRepository;
import utils.IdGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingService {

    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingValidator validator;

    public BookingService(StudentRepository studentRepository,
                          RoomRepository roomRepository,
                          BookingRepository bookingRepository) {

        this.studentRepository = studentRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.validator = new BookingValidator();
    }

    // Đặt phòng mới
    public Booking createBooking(String studentId,
                                 String roomId,
                                 LocalDate date,
                                 LocalTime startTime,
                                 LocalTime endTime,
                                 int participantCount) {

        Student student = studentRepository.findById(studentId);
        Room room = roomRepository.findById(roomId);
        TimeSlot timeSlot = new TimeSlot(date, startTime, endTime);

        validator.validateStudent(student);
        validator.validateRoom(room);
        validator.validateTimeSlot(timeSlot);
        validator.validateCapacity(room, participantCount);

        List<Booking> roomBookings = bookingRepository.findByRoomId(roomId);
        validator.validateOverlap(room, timeSlot, roomBookings);

        List<Booking> studentBookings = bookingRepository.findByStudentId(studentId);
        validator.validateDailyLimit(student, timeSlot, studentBookings);

        String bookingId = IdGenerator.generateBookingId();

        Booking booking = new Booking(
                bookingId,
                student,
                room,
                timeSlot,
                participantCount,
                "Đã đặt"
        );

        bookingRepository.save(booking);

        System.out.println("Dat phong thanh cong! Ma dat phong: " + bookingId);

        return booking;
    }

    // Hủy booking
    public void cancelBooking(String bookingId, String studentId) {

        Booking booking = bookingRepository.findById(bookingId);

        if (booking == null) {
            throw new BookingException("Ma dat phong khong ton tai!");
        }

        if ("Đã hủy".equals(booking.getStatus())) {
            throw new BookingException("Lich dat nay da bi huy truoc do!");
        }

        if (!booking.getStudent().getUserId().equals(studentId)) {
            throw new BookingException("Ban khong phai nguoi tao lich dat nay!");
        }

        boolean canceled = bookingRepository.cancel(bookingId);

        if (canceled) {
            System.out.println("Huy lich dat phong thanh cong!");
        }
    }

    public List<Booking> getBookingsByStudent(String studentId) {
        return bookingRepository.findByStudentId(studentId);
    }

    public List<Booking> getBookingsByRoom(String roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    public Booking findBooking(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getAll();
    }
}