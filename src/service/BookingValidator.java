package service;

import exception.BookingException;
import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public class BookingValidator {

    private static final long MAX_MINUTES_PER_DAY = 4 * 60;

    public void validateStudent(Student student) {

        if (student == null) {
            throw new BookingException("Sinh vien khong ton tai!");
        }
    }

    public void validateRoom(Room room) {

        if (room == null) {
            throw new BookingException("Phong khong ton tai!");
        }

        if (!room.isAvailable()) {
            throw new BookingException("Phong dang bao tri, khong the dat!");
        }
    }

    public void validateTimeSlot(TimeSlot timeSlot) {

        if (timeSlot == null || !timeSlot.isValid()) {
            throw new BookingException(
                    "Khung gio khong hop le (thieu du lieu hoac gio ket thuc truoc gio bat dau)!");
        }
    }

    public void validateCapacity(Room room, int numberOfPeople) {

        if (numberOfPeople <= 0) {
            throw new BookingException("So luong nguoi phai lon hon 0!");
        }

        if (numberOfPeople > room.getCapacity()) {
            throw new BookingException("So luong nguoi vuot qua suc chua cua phong!");
        }
    }

    public void validateOverlap(Room room,
                                TimeSlot timeSlot,
                                List<Booking> bookings) {

        for (Booking booking : bookings) {

            if (!booking.getRoom().getRoomId().equals(room.getRoomId())) {
                continue;
            }

            if (!"Đã đặt".equals(booking.getStatus())) {
                continue;
            }

            if (timeSlot.isOverlapping(booking.getTimeSlot())) {
                throw new BookingException(
                        "Phong da bi trung lich trong khoang thoi gian nay!");
            }
        }
    }

    public void validateDailyLimit(Student student,
                                   TimeSlot timeSlot,
                                   List<Booking> bookings) {

        LocalDate date = timeSlot.getDate();

        long totalMinutes = timeSlot.getHours() * 60L;

        for (Booking booking : bookings) {

            if (!booking.getStudent().getUserId().equals(student.getUserId())) {
                continue;
            }

            if (!"Đã đặt".equals(booking.getStatus())) {
                continue;
            }

            TimeSlot bookedSlot = booking.getTimeSlot();

            if (!bookedSlot.getDate().equals(date)) {
                continue;
            }

            totalMinutes += bookedSlot.getHours() * 60L;
        }

        if (totalMinutes > MAX_MINUTES_PER_DAY) {
            throw new BookingException("Sinh vien khong duoc dat qua 4 gio trong mot ngay!");
        }
    }
}