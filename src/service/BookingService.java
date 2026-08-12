package service;

import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private final List<Booking> bookings;
    private final StudentService studentService;
    private final RoomService roomService;
    private final FeeService feeService;

    public BookingService(StudentService studentService,
                          RoomService roomService,
                          FeeService feeService) {

        this.bookings = new ArrayList<>();
        this.studentService = studentService;
        this.roomService = roomService;
        this.feeService = feeService;
    }

    // Đặt phòng
    public Booking bookRoom(String studentId,
                            String roomId,
                            TimeSlot timeSlot,
                            int participantCount) {

        // Kiểm tra sinh viên
        Student student = studentService.findById(studentId);

        if (student == null) {
            System.out.println("Lỗi: Sinh viên không tồn tại.");
            return null;
        }

        // Kiểm tra phòng
        Room room = roomService.findById(roomId);

        if (room == null) {
            System.out.println("Lỗi: Phòng không tồn tại.");
            return null;
        }

        // Kiểm tra phòng có đang hoạt động không
        if (!room.isAvailable()) {
            System.out.println("Lỗi: Phòng đang bảo trì.");
            return null;
        }

        // Kiểm tra thời gian
        if (timeSlot == null || !timeSlot.isValid()) {
            System.out.println("Lỗi: Thời gian đặt không hợp lệ.");
            return null;
        }

        // Kiểm tra số người
        if (participantCount <= 0) {
            System.out.println("Lỗi: Số người phải lớn hơn 0.");
            return null;
        }

        if (participantCount > room.getCapacity()) {
            System.out.println(
                    "Lỗi: Số người vượt quá sức chứa phòng."
            );
            return null;
        }

        // Kiểm tra trùng lịch
        for (Booking booking : bookings) {

            if (!"Đã đặt".equalsIgnoreCase(booking.getStatus())) {
                continue;
            }

            if (!booking.getRoom().getRoomId()
                    .equalsIgnoreCase(roomId)) {
                continue;
            }

            if (booking.getTimeSlot().isOverlapping(timeSlot)) {
                System.out.println(
                        "Lỗi: Phòng đã bị trùng lịch."
                );
                return null;
            }
        }

        // Kiểm tra sinh viên đã đặt quá 4 giờ trong ngày chưa
        double totalHours = timeSlot.getDurationHours();

        for (Booking booking : bookings) {

            if (!"Đã đặt".equalsIgnoreCase(booking.getStatus())) {
                continue;
            }

            if (!booking.getStudent().getUserId()
                    .equalsIgnoreCase(studentId)) {
                continue;
            }

            if (!booking.getTimeSlot().getDate()
                    .equals(timeSlot.getDate())) {
                continue;
            }

            totalHours += booking.getTimeSlot().getDurationHours();
        }

        if (totalHours > 4) {
            System.out.println(
                    "Lỗi: Sinh viên không được đặt quá 4 giờ trong một ngày."
            );
            return null;
        }

        // Sinh mã đặt phòng
        String bookingId = generateBookingId();

        // Tạo booking
        Booking booking = new Booking(
                bookingId,
                student,
                room,
                timeSlot,
                participantCount,
                "Đã đặt"
        );

        // Lưu booking
        bookings.add(booking);

        System.out.println(
                "Đặt phòng thành công! Mã đặt phòng: "
                        + bookingId
        );

        return booking;
    }

    // Hủy lịch đặt
    public boolean cancelBooking(String bookingId,
                                 String studentId) {

        Booking booking = findById(bookingId);

        if (booking == null) {
            System.out.println(
                    "Lỗi: Không tìm thấy lịch đặt."
            );
            return false;
        }

        // Kiểm tra lịch có thuộc sinh viên không
        if (!booking.getStudent().getUserId()
                .equalsIgnoreCase(studentId)) {

            System.out.println(
                    "Lỗi: Lịch đặt không thuộc về sinh viên này."
            );
            return false;
        }

        // Kiểm tra đã hủy chưa
        if ("Đã hủy".equalsIgnoreCase(booking.getStatus())) {
            System.out.println(
                    "Lỗi: Lịch đặt đã được hủy trước đó."
            );
            return false;
        }

        booking.setStatus("Đã hủy");

        System.out.println(
                "Hủy lịch đặt thành công."
        );

        return true;
    }

    // Tìm booking theo mã
    public Booking findById(String bookingId) {

        if (bookingId == null) {
            return null;
        }

        for (Booking booking : bookings) {

            if (booking.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                return booking;
            }
        }

        return null;
    }

    // Lấy tất cả booking
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    // Lấy booking của một sinh viên
    public List<Booking> getBookingsByStudent(
            String studentId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {

            if (booking.getStudent().getUserId()
                    .equalsIgnoreCase(studentId)) {

                result.add(booking);
            }
        }

        return result;
    }

    // Lấy booking của một phòng
    public List<Booking> getBookingsByRoom(
            String roomId) {

        List<Booking> result = new ArrayList<>();

        for (Booking booking : bookings) {

            if (booking.getRoom().getRoomId()
                    .equalsIgnoreCase(roomId)) {

                result.add(booking);
            }
        }

        return result;
    }

    // Tính phí booking
    public double calculateFee(Booking booking) {

        if (booking == null) {
            return 0;
        }

        double hours =
                booking.getTimeSlot().getDurationHours();

        return feeService.calculateFee(
                booking.getRoom(),
                hours
        );
    }

    // Sinh mã booking
    private String generateBookingId() {

        return String.format(
                "BK%03d",
                bookings.size() + 1
        );
    }
}