package validator;

import model.Booking;
import model.Room;
import model.Student;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingValidatorImpl implements BookingValidator {

    private static final double MAX_BOOKING_HOURS_PER_DAY = 4.0;

    @Override
    public void validateStudent(Student student) {

        if (student == null) {
            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!"
            );
        }
    }

    @Override
    public void validateRoom(Room room) {

        if (room == null) {
            throw new IllegalArgumentException(
                    "Phòng không tồn tại!"
            );
        }
    }

    @Override
    public void validateTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        if (startTime == null) {
            throw new IllegalArgumentException(
                    "Thời gian bắt đầu không được để trống!"
            );
        }

        if (endTime == null) {
            throw new IllegalArgumentException(
                    "Thời gian kết thúc không được để trống!"
            );
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Thời gian kết thúc phải sau thời gian bắt đầu!"
            );
        }
    }

    @Override
    public void validateCapacity(
            Room room,
            int numberOfPeople
    ) {

        validateRoom(room);

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException(
                    "Số lượng người tham gia phải lớn hơn 0!"
            );
        }

        if (numberOfPeople > room.getCapacity()) {
            throw new IllegalArgumentException(
                    "Số lượng người tham gia vượt quá sức chứa của phòng!"
            );
        }
    }

    @Override
    public void validateOverlap(
            List<Booking> bookings,
            String roomId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã phòng không hợp lệ!"
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

            if (!roomId.equals(booking.getRoomId())) {
                continue;
            }

            LocalDateTime existingStart =
                    booking.getStartTime();

            LocalDateTime existingEnd =
                    booking.getEndTime();

            if (existingStart == null || existingEnd == null) {
                continue;
            }

            boolean isOverlap =
                    startTime.isBefore(existingEnd)
                            && endTime.isAfter(existingStart);

            if (isOverlap) {
                throw new IllegalArgumentException(
                        "Phòng đã được đặt trong khoảng thời gian này!"
                );
            }
        }
    }

    @Override
    public void validateDailyBookingHours(
            List<Booking> bookings,
            String studentId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã sinh viên không hợp lệ!"
            );
        }

        validateTime(startTime, endTime);

        LocalDate bookingDate = startTime.toLocalDate();

        double newBookingHours =
                calculateHours(startTime, endTime);

        double totalBookingHours = 0;

        if (bookings != null) {

            for (Booking booking : bookings) {

                if (booking == null) {
                    continue;
                }

                if (!studentId.equals(booking.getStudentId())) {
                    continue;
                }

                LocalDateTime existingStart =
                        booking.getStartTime();

                LocalDateTime existingEnd =
                        booking.getEndTime();

                if (existingStart == null || existingEnd == null) {
                    continue;
                }

                if (!bookingDate.equals(existingStart.toLocalDate())) {
                    continue;
                }

                double bookingHours =
                        calculateHours(
                                existingStart,
                                existingEnd
                        );

                totalBookingHours += bookingHours;
            }
        }

        if (totalBookingHours + newBookingHours
                > MAX_BOOKING_HOURS_PER_DAY) {

            throw new IllegalArgumentException(
                    "Sinh viên không được đặt quá 4 giờ trong một ngày!"
            );
        }
    }

    private double calculateHours(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        return Duration
                .between(startTime, endTime)
                .toMinutes() / 60.0;
    }
}
public interface BookingValidator {

    void validateStudent(Student student);

    void validateRoom(Room room);

    void validateTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    void validateCapacity(
            Room room,
            int numberOfPeople
    );

    void validateOverlap(
            List<Booking> bookings,
            String roomId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    void validateDailyBookingHours(
            List<Booking> bookings,
            String studentId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}