package service;

import model.Booking;

public class DefaultBookingValidator
        implements BookingValidator {

    @Override
    public void validate(Booking booking) {

        if (booking == null) {

            throw new IllegalArgumentException(
                    "Booking khong hop le!"
            );
        }

        if (booking.getStudentId() == null
                || booking.getStudentId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ma sinh vien khong duoc rong!"
            );
        }

        if (booking.getRoomId() == null
                || booking.getRoomId().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ma phong khong duoc rong!"
            );
        }

        if (booking.getDate() == null
                || booking.getDate().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Ngay dat khong duoc rong!"
            );
        }

        if (booking.getStartTime() == null
                || booking.getStartTime().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Gio bat dau khong duoc rong!"
            );
        }

        if (booking.getEndTime() == null
                || booking.getEndTime().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Gio ket thuc khong duoc rong!"
            );
        }

        if (booking.getNumberOfPeople() <= 0) {

            throw new IllegalArgumentException(
                    "So nguoi phai lon hon 0!"
            );
        }
    }
}