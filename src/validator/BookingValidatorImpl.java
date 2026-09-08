package validator;

import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingValidatorImpl implements BookingValidator {

    private static final double MAX_BOOKING_HOURS_PER_DAY = 4.0;
    private static final String BOOKED_STATUS = "Đã đặt";

    @Override
    public void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại!");
        }

        if (isBlank(student.getUserId())) {
            throw new IllegalArgumentException("Mã sinh viên không hợp lệ!");
        }
    }

    @Override
    public void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Phòng không tồn tại!");
        }

        if (isBlank(room.getRoomId())) {
            throw new IllegalArgumentException("Mã phòng không hợp lệ!");
        }

        if (room.getCapacity() <= 0) {
            throw new IllegalArgumentException("Sức chứa của phòng phải lớn hơn 0!");
        }

        if (!room.isAvailable()) {
            throw new IllegalArgumentException("Phòng hiện không hoạt động, không thể đặt!");
        }
    }

    @Override
    public void validateTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Thời gian bắt đầu không được để trống!");
        }

        if (endTime == null) {
            throw new IllegalArgumentException("Thời gian kết thúc không được để trống!");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Thời gian kết thúc phải sau thời gian bắt đầu!"
            );
        }

        if (!startTime.toLocalDate().equals(endTime.toLocalDate())) {
            throw new IllegalArgumentException(
                    "Lịch đặt phòng phải bắt đầu và kết thúc trong cùng một ngày!"
            );
        }
    }

    @Override
    public void validateCapacity(Room room, int numberOfPeople) {
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
            LocalDateTime endTime) {

        if (isBlank(roomId)) {
            throw new IllegalArgumentException("Mã phòng không hợp lệ!");
        }

        validateTime(startTime, endTime);

        if (bookings == null || bookings.isEmpty()) {
            return;
        }

        for (Booking booking : bookings) {
            if (booking == null || !isBooked(booking)) {
                continue;
            }

            Room existingRoom = booking.getRoom();
            TimeSlot slot = booking.getTimeSlot();

            if (existingRoom == null || slot == null) {
                continue;
            }

            if (!roomId.equals(existingRoom.getRoomId())) {
                continue;
            }

            LocalDateTime existingStart = toStartDateTime(slot);
            LocalDateTime existingEnd = toEndDateTime(slot);

            if (existingStart == null || existingEnd == null) {
                continue;
            }

            boolean overlap = startTime.isBefore(existingEnd)
                    && endTime.isAfter(existingStart);

            if (overlap) {
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
            LocalDateTime endTime) {

        if (isBlank(studentId)) {
            throw new IllegalArgumentException("Mã sinh viên không hợp lệ!");
        }

        validateTime(startTime, endTime);

        LocalDate bookingDate = startTime.toLocalDate();
        double totalHours = calculateHours(startTime, endTime);

        if (bookings != null) {
            for (Booking booking : bookings) {
                if (booking == null || !isBooked(booking)) {
                    continue;
                }

                Student existingStudent = booking.getStudent();
                TimeSlot slot = booking.getTimeSlot();

                if (existingStudent == null || slot == null) {
                    continue;
                }

                if (!studentId.equals(existingStudent.getUserId())) {
                    continue;
                }

                LocalDateTime existingStart = toStartDateTime(slot);
                LocalDateTime existingEnd = toEndDateTime(slot);

                if (existingStart == null || existingEnd == null) {
                    continue;
                }

                if (!bookingDate.equals(existingStart.toLocalDate())) {
                    continue;
                }

                totalHours += calculateHours(existingStart, existingEnd);
            }
        }

        if (totalHours > MAX_BOOKING_HOURS_PER_DAY) {
            throw new IllegalArgumentException(
                    "Sinh viên không được đặt quá 4 giờ trong một ngày!"
            );
        }
    }

    private boolean isBooked(Booking booking) {
        return booking.getStatus() != null
                && BOOKED_STATUS.equalsIgnoreCase(booking.getStatus().trim());
    }

    private LocalDateTime toStartDateTime(TimeSlot slot) {
        if (slot.getDate() == null || slot.getStartTime() == null) {
            return null;
        }

        return LocalDateTime.of(slot.getDate(), slot.getStartTime());
    }

    private LocalDateTime toEndDateTime(TimeSlot slot) {
        if (slot.getDate() == null || slot.getEndTime() == null) {
            return null;
        }

        return LocalDateTime.of(slot.getDate(), slot.getEndTime());
    }

    private double calculateHours(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).toMinutes() / 60.0;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
