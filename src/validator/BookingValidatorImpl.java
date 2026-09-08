package validator;

import model.Booking;
import model.Room;
import model.Student;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingValidatorImpl implements BookingValidator {

    private static final double MAX_HOURS_PER_DAY = 4.0;

    @Override
    public void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException(
                    "Sinh vien khong ton tai!"
            );
        }
    }

    @Override
    public void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException(
                    "Phong khong ton tai!"
            );
        }
    }

    @Override
    public void validateTime(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Thoi gian dat phong khong duoc de trong!"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Thoi gian ket thuc phai sau thoi gian bat dau!"
            );
        }
    }

    @Override
    public void validateCapacity(
            Room room,
            int numberOfPeople) {

        validateRoom(room);

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

    @Override
    public void validateOverlap(
            List<Booking> bookings,
            String roomId,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ma phong khong hop le!"
            );
        }

        validateTime(startTime, endTime);

        if (bookings == null || bookings.isEmpty()) {
            return;
        }

        for (Booking booking : bookings) {

            if (booking == null) {
                continue;
            }

            if (booking.getRoomId() == null) {
                continue;
            }

            if (!roomId.equals(booking.getRoomId())) {
                continue;
            }

            LocalDateTime oldStart = booking.getStartTime();
            LocalDateTime oldEnd = booking.getEndTime();

            if (oldStart == null || oldEnd == null) {
                continue;
            }

            /*
             * Hai khoảng thời gian bị trùng khi:
             *
             * startMoi < endCu
             * và
             * endMoi > startCu
             */
            boolean overlap =
                    startTime.isBefore(oldEnd)
                            && endTime.isAfter(oldStart);

            if (overlap) {
                throw new IllegalArgumentException(
                        "Phong da duoc dat trong khoang thoi gian nay!"
                );
            }
        }
    }

    @Override
    public void validateDailyBookingHours(
            List<Booking> bookings,
            String studentId,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Ma sinh vien khong hop le!"
            );
        }

        validateTime(startTime, endTime);

        LocalDate bookingDate = startTime.toLocalDate();

        double totalHours = calculateHours(
                startTime,
                endTime
        );

        if (bookings != null) {

            for (Booking booking : bookings) {

                if (booking == null) {
                    continue;
                }

                if (booking.getStudentId() == null) {
                    continue;
                }

                if (!studentId.equals(booking.getStudentId())) {
                    continue;
                }

                LocalDateTime oldStart = booking.getStartTime();
                LocalDateTime oldEnd = booking.getEndTime();

                if (oldStart == null || oldEnd == null) {
                    continue;
                }

                if (!bookingDate.equals(oldStart.toLocalDate())) {
                    continue;
                }

                totalHours += calculateHours(
                        oldStart,
                        oldEnd
                );
            }
        }

        if (totalHours > MAX_HOURS_PER_DAY) {
            throw new IllegalArgumentException(
                    "Sinh vien khong duoc dat qua 4 gio trong mot ngay!"
            );
        }
    }

    private double calculateHours(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        return Duration.between(
                startTime,
                endTime
        ).toMinutes() / 60.0;
    }
}