package validator;

import model.Booking;
import model.Room;
import model.Student;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kiểm tra các điều kiện trước khi tạo lịch đặt phòng.
 */
public interface BookingValidator {

    void validateStudent(Student student);

    void validateRoom(Room room);

    void validateTime(LocalDateTime startTime, LocalDateTime endTime);

    void validateCapacity(Room room, int numberOfPeople);

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
