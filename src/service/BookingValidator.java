package service;

import model.Booking;

import java.time.LocalDateTime;

public class BookingValidator {

    public void validate(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Lịch đặt không tồn tại!");
        }

        if (booking.getStudent() == null) {
            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!");
        }

        if (booking.getRoom() == null) {
            throw new IllegalArgumentException(
                    "Phòng không tồn tại!");
        }

        if (!booking.getRoom().isAvailable()) {
            throw new IllegalArgumentException(
                    "Phòng đang bảo trì!");
        }

        LocalDateTime start =
                booking.getStartTime();

        LocalDateTime end =
                booking.getEndTime();

        if (start == null || end == null) {
            throw new IllegalArgumentException(
                    "Thời gian đặt không hợp lệ!");
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException(
                    "Thời gian kết thúc phải lớn hơn thời gian bắt đầu!");
        }

        if (booking.getNumberOfPeople() <= 0) {
            throw new IllegalArgumentException(
                    "Số lượng người phải lớn hơn 0!");
        }

        if (booking.getNumberOfPeople()
                > booking.getRoom().getCapacity()) {

            throw new IllegalArgumentException(
                    "Số lượng người vượt quá sức chứa phòng!");
        }
    }
}