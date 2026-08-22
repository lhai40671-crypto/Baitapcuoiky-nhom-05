package service;

import model.Booking;
import model.Room;
import model.Student;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class BookingValidator {

    public void validateStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException(
                    "Sinh vien khong ton tai!"
            );
        }
    }

    public void validateRoom(Room room) {

        if (room == null) {
            throw new IllegalArgumentException(
                    "Phong khong ton tai!"
            );
        }

        if (!room.isActive()) {
            throw new IllegalArgumentException(
                    "Phong dang bao tri, khong the dat!"
            );
        }
    }

    public void validateTime(
            java.time.LocalDateTime startTime,
            java.time.LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Thoi gian khong duoc de trong!"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Thoi gian ket thuc phai lon hon thoi gian bat dau!"
            );
        }
    }

    public void validateCapacity(
            Room room,
            int numberOfPeople) {

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException(
                    "So luong nguoi phai lon hon 0!"
            );
        }

        if (numberOfPeople > room.getCapacity()) {
            throw new IllegalArgumentException(
                    "So luong nguoi vuot qua suc chua cua phong!"
            );
        }
    }

    public void validateOverlap(
            Room room,
            java.time.LocalDateTime startTime,
            java.time.LocalDateTime endTime,
            List<Booking> bookings) {

        for (Booking booking : bookings) {

            if (!booking.getRoom().getRoomId()
                    .equals(room.getRoomId())) {
                continue;
            }

            if (!booking.getStatus().equals("Đã đặt")) {
                continue;
            }

            boolean overlap =
                    startTime.isBefore(booking.getEndTime())
                            && endTime.isAfter(booking.getStartTime());

            if (overlap) {
                throw new IllegalArgumentException(
                        "Phong da bi trung lich trong khoang thoi gian nay!"
                );
            }
        }
    }

    public void validateDailyLimit(
            Student student,
            java.time.LocalDateTime startTime,
            java.time.LocalDateTime endTime,
            List<Booking> bookings) {

        LocalDate date = startTime.toLocalDate();

        long newMinutes = Duration.between(
                startTime,
                endTime
        ).toMinutes();

        long totalMinutes = newMinutes;

        for (Booking booking : bookings) {

            if (!booking.getStudent()
                    .getStudentId()
                    .equals(student.getStudentId())) {
                continue;
            }

            if (!booking.getStatus().equals("Đã đặt")) {
                continue;
            }

            if (!booking.getStartTime()
                    .toLocalDate()
                    .equals(date)) {
                continue;
            }

            long minutes = Duration.between(
                    booking.getStartTime(),
                    booking.getEndTime()
            ).toMinutes();

            totalMinutes += minutes;
        }

        if (totalMinutes > 4 * 60) {
            throw new IllegalArgumentException(
                    "Sinh vien khong duoc dat qua 4 gio trong mot ngay!"
            );
        }
    }
}