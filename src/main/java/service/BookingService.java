package service;

import model.Booking;
import model.Room;
import model.Student;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private List<Booking> bookings = new ArrayList<>();

    public List<Booking> getBookings() {
        return bookings;
    }

    public void loadBookings(List<Booking> bookings) {
        this.bookings.clear();
        this.bookings.addAll(bookings);
    }

    public void bookRoom(
            Booking booking,
            List<Student> students,
            List<Room> rooms) {

        if (booking.getStudent() == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại!");
        }

        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("Phòng không tồn tại!");
        }

        if (!booking.getRoom().isAvailable()) {
            throw new IllegalArgumentException("Phòng không khả dụng!");
        }

        for (Booking b : bookings) {

            if (b.getRoom().getRoomId()
                    .equals(booking.getRoom().getRoomId())
                    && b.getDate().equals(booking.getDate())
                    && booking.getStartTime().compareTo(b.getEndTime()) < 0
                    && booking.getEndTime().compareTo(b.getStartTime()) > 0) {

                throw new IllegalArgumentException(
                        "Phòng đã bị trùng lịch!"
                );
            }
        }

        booking.setStatus("Đã đặt");
        bookings.add(booking);

        System.out.println("Đặt phòng thành công!");
    }

    public void cancelBooking(
            String bookingId,
            Student student) {

        for (Booking booking : bookings) {

            if (booking.getBookingId().equals(bookingId)
                    && booking.getStudent().getId()
                    .equals(student.getId())) {

                booking.setStatus("Đã hủy");

                System.out.println("Hủy lịch thành công!");
                return;
            }
        }

        throw new IllegalArgumentException(
                "Không tìm thấy lịch đặt!"
        );
    }
}