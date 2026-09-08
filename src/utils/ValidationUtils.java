package utils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ValidationUtils {

    private ValidationUtils() {
        // Không cho phép tạo đối tượng
    }

    // Kiểm tra chuỗi không được rỗng
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Kiểm tra mã sinh viên
    public static boolean isValidStudentId(String studentId) {
        return isNotEmpty(studentId)
                && studentId.trim().matches("[A-Za-z0-9]+");
    }

    // Kiểm tra số điện thoại
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) {
            return false;
        }

        return phone.trim().matches("0[0-9]{9,10}");
    }

    // Kiểm tra email
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false;
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern.matches(emailRegex, email.trim());
    }

    // Kiểm tra thời gian đặt phòng
    // Thời gian kết thúc phải lớn hơn thời gian bắt đầu
    public static boolean isValidTime(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            return false;
        }

        return endTime.isAfter(startTime);
    }

    // Kiểm tra số lượng người tham gia
    public static boolean isValidCapacity(
            int numberOfPeople,
            int roomCapacity) {

        return numberOfPeople > 0
                && roomCapacity > 0
                && numberOfPeople <= roomCapacity;
    }

    // Kiểm tra số giờ đặt trong ngày
    // Theo business rule: không quá 4 giờ/ngày
    public static boolean isValidDailyBookingHours(double hours) {
        return hours > 0 && hours <= 4;
    }

    // Kiểm tra số giờ đặt phòng
    public static boolean isValidDuration(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (!isValidTime(startTime, endTime)) {
            return false;
        }

        long minutes =
                java.time.Duration.between(startTime, endTime).toMinutes();

        return minutes > 0;
    }

    // Kiểm tra tên
    public static boolean isValidName(String name) {
        return isNotEmpty(name)
                && name.trim().length() >= 2;
    }

    // Kiểm tra tầng của phòng
    public static boolean isValidFloor(int floor) {
        return floor >= 0;
    }

    // Kiểm tra sức chứa phòng
    public static boolean isValidRoomCapacity(int capacity) {
        return capacity > 0;
    }

    // Kiểm tra mã phòng
    public static boolean isValidRoomId(String roomId) {
        return isNotEmpty(roomId)
                && roomId.trim().matches("[A-Za-z0-9_-]+");
    }
}