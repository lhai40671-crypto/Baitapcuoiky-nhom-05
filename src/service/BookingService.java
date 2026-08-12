package service;

import model.Booking;
import model.Room;
import model.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingService {

    private final List<Booking> bookings;
    private final BookingValidator validator;

    public BookingService(List<Booking> bookings,
                          BookingValidator validator) {

        this.bookings = bookings;
        this.validator = validator;
    }

    // ============================
    // ĐẶT PHÒNG
    // ============================

    public Booking createBooking(Booking booking) {

        validator.validate(booking);

        if (isRoomBusy(
                booking.getRoom(),
                booking.getStartTime(),
                booking.getEndTime())) {

            throw new IllegalArgumentException(
                    "Phòng đã bị trùng lịch!");
        }

        double totalHours =
                getTotalHoursInDay(
                        booking.getStudent(),
                        booking.getStartTime());

        if (totalHours + booking.getHours() > 4) {

            throw new IllegalArgumentException(
                    "Sinh viên không được đặt quá 4 giờ trong một ngày!");
        }

        String bookingId =
                "BK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase();

        booking.setBookingId(bookingId);
        booking.setStatus("Đã đặt");

        bookings.add(booking);

        booking.getStudent()
                .addBooking(booking);

        return booking;
    }

    // ============================
    // HỦY PHÒNG
    // ============================

    public void cancelBooking(
            String bookingId,
            Student student) {

        Booking booking =
                findById(bookingId);

        if (booking == null) {

            throw new IllegalArgumentException(
                    "Mã đặt phòng không tồn tại!");
        }

        if (student == null) {

            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!");
        }

        if (!booking.getStudent()
                .getStudentId()
                .equals(student.getStudentId())) {

            throw new IllegalArgumentException(
                    "Lịch đặt không thuộc về sinh viên này!");
        }

        if ("Đã hủy".equals(
                booking.getStatus())) {

            throw new IllegalArgumentException(
                    "Lịch đặt đã bị hủy trước đó!");
        }

        booking.setStatus("Đã hủy");
    }

    // ============================
    // TÌM BOOKING
    // ============================

    public Booking findById(String bookingId) {

        if (bookingId == null) {
            return null;
        }

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equals(bookingId)) {

                return booking;
            }
        }

        return null;
    }

    // ============================
    // KIỂM TRA TRÙNG LỊCH
    // ============================

    private boolean isRoomBusy(
            Room room,
            LocalDateTime start,
            LocalDateTime end) {

        for (Booking booking : bookings) {

            if (!booking.getRoom()
                    .getRoomId()
                    .equals(room.getRoomId())) {

                continue;
            }

            if ("Đã hủy".equals(
                    booking.getStatus())) {

                continue;
            }

            boolean overlap =
                    start.isBefore(
                            booking.getEndTime())
                            &&
                            end.isAfter(
                                    booking.getStartTime());

            if (overlap) {
                return true;
            }
        }

        return false;
    }

    // ============================
    // TỔNG GIỜ TRONG NGÀY
    // ============================

    private double getTotalHoursInDay(
            Student student,
            LocalDateTime date) {

        double total = 0;

        LocalDate targetDate =
                date.toLocalDate();

        for (Booking booking : bookings) {

            if (!booking.getStudent()
                    .getStudentId()
                    .equals(student.getStudentId())) {

                continue;
            }

            if ("Đã hủy".equals(
                    booking.getStatus())) {

                continue;
            }

            if (booking.getStartTime()
                    .toLocalDate()
                    .equals(targetDate)) {

                total += booking.getHours();
            }
        }

        return total;
    }

    // ============================
    // BOOKING CỦA SINH VIÊN
    // ============================

    public List<Booking> getBookingsByStudent(
            Student student) {

        List<Booking> result =
                new ArrayList<>();

        if (student == null) {
            return result;
        }

        for (Booking booking : bookings) {

            if (booking.getStudent()
                    .getStudentId()
                    .equals(student.getStudentId())) {

                result.add(booking);
            }
        }

        return result;
    }

    // ============================
    // BOOKING CÒN HIỆU LỰC
    // ============================

    public List<Booking> getActiveBookings() {

        List<Booking> result =
                new ArrayList<>();

        for (Booking booking : bookings) {

            if ("Đã đặt".equals(
                    booking.getStatus())) {

                result.add(booking);
            }
        }

        return result;
    }

    // ============================
    // LẤY TẤT CẢ
    // ============================

    public List<Booking> getBookings() {

        return new ArrayList<>(bookings);
    }
}