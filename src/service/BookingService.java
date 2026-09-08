package service;

import model.Booking;
import model.Room;
import model.Student;

import repository.BookingRepository;
import repository.RoomRepository;
import repository.StudentRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingService {

    private BookingRepository bookingRepository;
    private StudentRepository studentRepository;
    private RoomRepository roomRepository;

    private DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");

    // =========================
    // CONSTRUCTOR
    // =========================

    public BookingService() {

        bookingRepository =
                new BookingRepository();

        studentRepository =
                new StudentRepository();

        roomRepository =
                new RoomRepository();
    }

    // =========================
    // ĐẶT PHÒNG
    // =========================

    public boolean createBooking(
            String studentId,
            String roomId,
            String date,
            String startTime,
            String endTime,
            int numberOfPeople) {

        // 1. Kiểm tra sinh viên
        Student student =
                studentRepository.findById(studentId);

        if (student == null) {

            System.out.println(
                    "Loi: Sinh vien khong ton tai!"
            );

            return false;
        }

        // 2. Kiểm tra phòng
        Room room =
                roomRepository.findById(roomId);

        if (room == null) {

            System.out.println(
                    "Loi: Phong khong ton tai!"
            );

            return false;
        }

        // 3. Kiểm tra trạng thái phòng
        if (!room.getStatus()
                .equalsIgnoreCase("Dang hoat dong")) {

            System.out.println(
                    "Loi: Phong dang bao tri!"
            );

            return false;
        }

        // 4. Kiểm tra số người
        if (numberOfPeople <= 0) {

            System.out.println(
                    "Loi: So nguoi phai lon hon 0!"
            );

            return false;
        }

        if (numberOfPeople > room.getCapacity()) {

            System.out.println(
                    "Loi: So nguoi vuot qua suc chua phong!"
            );

            return false;
        }

        // 5. Kiểm tra thời gian
        LocalTime start;
        LocalTime end;

        try {

            start = LocalTime.parse(
                    startTime,
                    timeFormatter
            );

            end = LocalTime.parse(
                    endTime,
                    timeFormatter
            );

        } catch (Exception e) {

            System.out.println(
                    "Loi: Thoi gian khong hop le!"
            );

            return false;
        }

        if (!end.isAfter(start)) {

            System.out.println(
                    "Loi: Thoi gian ket thuc phai lon hon thoi gian bat dau!"
            );

            return false;
        }

        // 6. Kiểm tra trùng lịch
        if (isOverlapping(
                roomId,
                date,
                start,
                end)) {

            System.out.println(
                    "Loi: Phong da bi trung lich!"
            );

            return false;
        }

        // 7. Kiểm tra giới hạn 4 giờ/ngày
        double bookedHours =
                getBookedHoursInDay(
                        studentId,
                        date
                );

        double newBookingHours =
                Duration.between(
                        start,
                        end
                ).toMinutes() / 60.0;

        if (bookedHours + newBookingHours > 4) {

            System.out.println(
                    "Loi: Sinh vien khong duoc dat qua 4 gio trong mot ngay!"
            );

            return false;
        }

        // 8. Tạo mã booking
        String bookingId =
                generateBookingId();

        // 9. Tạo Booking
        Booking booking = new Booking(
                bookingId,
                studentId,
                roomId,
                date,
                startTime,
                endTime,
                numberOfPeople,
                "Da dat"
        );

        // 10. Lưu booking
        bookingRepository.save(booking);

        System.out.println(
                "Dat phong thanh cong!"
        );

        System.out.println(
                "Ma dat phong: "
                        + bookingId
        );

        return true;
    }

    // =========================
    // KIỂM TRA TRÙNG LỊCH
    // =========================

    private boolean isOverlapping(
            String roomId,
            String date,
            LocalTime newStart,
            LocalTime newEnd) {

        List<Booking> bookings =
                bookingRepository.getAll();

        for (Booking booking : bookings) {

            if (booking.getStatus()
                    .equalsIgnoreCase("Da huy")) {

                continue;
            }

            if (!booking.getRoomId()
                    .equalsIgnoreCase(roomId)) {

                continue;
            }

            if (!booking.getDate()
                    .equals(date)) {

                continue;
            }

            LocalTime oldStart =
                    LocalTime.parse(
                            booking.getStartTime(),
                            timeFormatter
                    );

            LocalTime oldEnd =
                    LocalTime.parse(
                            booking.getEndTime(),
                            timeFormatter
                    );

            if (newStart.isBefore(oldEnd)
                    && newEnd.isAfter(oldStart)) {

                return true;
            }
        }

        return false;
    }

    // =========================
    // TÍNH SỐ GIỜ ĐÃ ĐẶT
    // =========================

    private double getBookedHoursInDay(
            String studentId,
            String date) {

        double totalHours = 0;

        List<Booking> bookings =
                bookingRepository
                        .findByStudentId(studentId);

        for (Booking booking : bookings) {

            if (booking.getStatus()
                    .equalsIgnoreCase("Da huy")) {

                continue;
            }

            if (!booking.getDate()
                    .equals(date)) {

                continue;
            }

            LocalTime start =
                    LocalTime.parse(
                            booking.getStartTime(),
                            timeFormatter
                    );

            LocalTime end =
                    LocalTime.parse(
                            booking.getEndTime(),
                            timeFormatter
                    );

            double hours =
                    Duration.between(
                            start,
                            end
                    ).toMinutes() / 60.0;

            totalHours += hours;
        }

        return totalHours;
    }

    // =========================
    // SINH MÃ BOOKING
    // =========================

    private String generateBookingId() {

        List<Booking> bookings =
                bookingRepository.getAll();

        int max = 0;

        for (Booking booking : bookings) {

            String id =
                    booking.getBookingId();

            if (id.startsWith("BK")) {

                try {

                    int number =
                            Integer.parseInt(
                                    id.substring(2)
                            );

                    if (number > max) {
                        max = number;
                    }

                } catch (NumberFormatException e) {

                    // Bỏ qua mã không hợp lệ
                }
            }
        }

        return String.format(
                "BK%03d",
                max + 1
        );
    }

    // =========================
    // XEM BOOKING CỦA SINH VIÊN
    // =========================

    public List<Booking> getBookingsByStudent(
            String studentId) {

        return bookingRepository
                .findByStudentId(studentId);
    }

    // =========================
    // XEM BOOKING THEO PHÒNG VÀ NGÀY
    // =========================

    public List<Booking> getBookingsByRoomAndDate(
            String roomId,
            String date) {

        List<Booking> result =
                new java.util.ArrayList<>();

        List<Booking> bookings =
                bookingRepository.getAll();

        for (Booking booking : bookings) {

            // Bỏ qua lịch đã hủy
            if (booking.getStatus()
                    .equalsIgnoreCase("Da huy")) {

                continue;
            }

            // Kiểm tra phòng
            if (!booking.getRoomId()
                    .equalsIgnoreCase(roomId)) {

                continue;
            }

            // Kiểm tra ngày
            if (!booking.getDate()
                    .equals(date)) {

                continue;
            }

            result.add(booking);
        }

        return result;
    }

    // =========================
    // HỦY BOOKING
    // =========================

    public boolean cancelBooking(
            String bookingId,
            String studentId) {

        Booking booking =
                bookingRepository
                        .findById(bookingId);

        if (booking == null) {

            System.out.println(
                    "Loi: Ma dat phong khong ton tai!"
            );

            return false;
        }

        if (booking.getStatus()
                .equalsIgnoreCase("Da huy")) {

            System.out.println(
                    "Loi: Lich dat da bi huy truoc do!"
            );

            return false;
        }

        if (!booking.getStudentId()
                .equalsIgnoreCase(studentId)) {

            System.out.println(
                    "Loi: Ban khong phai nguoi tao lich dat!"
            );

            return false;
        }

        booking.setStatus("Da huy");

        List<Booking> bookings =
                bookingRepository.getAll();

        for (Booking item : bookings) {

            if (item.getBookingId()
                    .equalsIgnoreCase(bookingId)) {

                item.setStatus("Da huy");
            }
        }

        bookingRepository.saveAll(bookings);

        System.out.println(
                "Huy lich dat thanh cong!"
        );

        return true;
    }
}