package main;

import model.*;
import repository.*;
import service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm");

    private static final List<Student> students =
            new ArrayList<>();

    private static final List<Room> rooms =
            new ArrayList<>();

    private static final List<Booking> bookings =
            new ArrayList<>();

    private static final BookingService bookingService =
            new BookingService(
                    bookings,
                    new BookingValidator());

    private static final FeeService feeService =
            new FeeService();

    public static void main(String[] args) {

        createSampleData();

        while (true) {

            showMenu();

            int choice =
                    readInt("Chọn chức năng: ");

            try {

                switch (choice) {

                    case 1:
                        showStudents();
                        break;

                    case 2:
                        showRooms();
                        break;

                    case 3:
                        findRoom();
                        break;

                    case 4:
                        createBooking();
                        break;

                    case 5:
                        cancelBooking();
                        break;

                    case 6:
                        showMyBookings();
                        break;

                    case 7:
                        showAllBookings();
                        break;

                    case 8:
                        saveData();
                        break;

                    case 9:
                        loadData();
                        break;

                    case 0:
                        System.out.println(
                                "Đã thoát chương trình.");
                        return;

                    default:
                        System.out.println(
                                "Chức năng không hợp lệ!");
                }

            } catch (Exception e) {

                System.out.println(
                        "LỖI: "
                                + e.getMessage());
            }
        }
    }

    // ==============================
    // MENU
    // ==============================

    private static void showMenu() {

        System.out.println();
        System.out.println(
                "====================================");
        System.out.println(
                " HỆ THỐNG ĐẶT PHÒNG HỌC NHÓM");
        System.out.println(
                "====================================");

        System.out.println(
                "1. Xem danh sách sinh viên");
        System.out.println(
                "2. Xem danh sách phòng");
        System.out.println(
                "3. Tìm phòng");
        System.out.println(
                "4. Đặt phòng");
        System.out.println(
                "5. Hủy lịch đặt");
        System.out.println(
                "6. Xem lịch đặt của sinh viên");
        System.out.println(
                "7. Xem tất cả lịch đặt");
        System.out.println(
                "8. Lưu dữ liệu");
        System.out.println(
                "9. Đọc dữ liệu");
        System.out.println(
                "0. Thoát");

        System.out.println(
                "====================================");
    }

    // ==============================
    // DỮ LIỆU MẪU
    // ==============================

    private static void createSampleData() {

        Student s1 =
                new Student(
                        "SV001",
                        "Nguyen Van An",
                        "0912345678",
                        "CNTT1",
                        "an@gmail.com");

        Student s2 =
                new Student(
                        "SV002",
                        "Tran Van Binh",
                        "0987654321",
                        "CNTT2",
                        "binh@gmail.com");

        students.add(s1);
        students.add(s2);

        rooms.add(
                new NormalRoom(
                        "P101",
                        "Phòng học nhóm 101",
                        1,
                        10,
                        "Đang hoạt động"));

        rooms.add(
                new ProjectorRoom(
                        "P201",
                        "Phòng máy chiếu 201",
                        2,
                        20,
                        "Đang hoạt động"));

        rooms.add(
                new SeminarRoom(
                        "P301",
                        "Phòng seminar 301",
                        3,
                        30,
                        "Đang bảo trì"));
    }

    // ==============================
    // HIỂN THỊ SINH VIÊN
    // ==============================

    private static void showStudents() {

        System.out.println(
                "\n===== DANH SÁCH SINH VIÊN =====");

        for (Student student : students) {

            System.out.println(student);
        }
    }

    // ==============================
    // HIỂN THỊ PHÒNG
    // ==============================

    private static void showRooms() {

        System.out.println(
                "\n===== DANH SÁCH PHÒNG =====");

        for (Room room : rooms) {

            System.out.println(room);
        }
    }

    // ==============================
    // TÌM PHÒNG
    // ==============================

    private static void findRoom() {

        System.out.println(
                "\n1. Tìm theo mã phòng");
        System.out.println(
                "2. Tìm theo loại phòng");

        int choice =
                readInt("Chọn: ");

        if (choice == 1) {

            String id =
                    readString("Nhập mã phòng: ");

            Room room = null;

            for (Room r : rooms) {

                if (r.getRoomId()
                        .equalsIgnoreCase(id)) {

                    room = r;
                    break;
                }
            }

            if (room == null) {

                System.out.println(
                        "Không tìm thấy phòng!");

            } else {

                System.out.println(room);
            }

        } else if (choice == 2) {

            String type =
                    readString(
                            "Nhập loại (NORMAL/PROJECTOR/SEMINAR): ");

            boolean found = false;

            for (Room room : rooms) {

                if (room.getRoomType()
                        .equalsIgnoreCase(type)) {

                    System.out.println(room);
                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "Không tìm thấy phòng!");
            }

        } else {

            System.out.println(
                    "Lựa chọn không hợp lệ!");
        }
    }

    // ==============================
    // ĐẶT PHÒNG
    // ==============================

    private static void createBooking() {

        String studentId =
                readString(
                        "Nhập mã sinh viên: ");

        Student student =
                findStudent(studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!");
        }

        String roomId =
                readString(
                        "Nhập mã phòng: ");

        Room room =
                findRoomById(roomId);

        if (room == null) {

            throw new IllegalArgumentException(
                    "Phòng không tồn tại!");
        }

        String startInput =
                readString(
                        "Thời gian bắt đầu (yyyy-MM-dd HH:mm): ");

        String endInput =
                readString(
                        "Thời gian kết thúc (yyyy-MM-dd HH:mm): ");

        LocalDateTime start =
                LocalDateTime.parse(
                        startInput,
                        FORMATTER);

        LocalDateTime end =
                LocalDateTime.parse(
                        endInput,
                        FORMATTER);

        int numberOfPeople =
                readInt(
                        "Số người tham gia: ");

        String purpose =
                readString(
                        "Mục đích sử dụng: ");

        String note =
                readString(
                        "Ghi chú: ");

        TimeSlot timeSlot =
                new TimeSlot(
                        start,
                        end);

        BookingDetail detail =
                new BookingDetail(
                        numberOfPeople,
                        purpose,
                        note);

        Booking booking =
                new Booking(
                        null,
                        student,
                        room,
                        timeSlot,
                        detail,
                        "Đã đặt");

        Booking result =
                bookingService.createBooking(
                        booking);

        System.out.println(
                "\nĐẶT PHÒNG THÀNH CÔNG!");

        System.out.println(result);

        double fee =
                feeService.calculateFee(result);

        System.out.println(
                "Tổng phí: "
                        + fee
                        + " VNĐ");
    }

    // ==============================
    // HỦY PHÒNG
    // ==============================

    private static void cancelBooking() {

        String studentId =
                readString(
                        "Mã sinh viên: ");

        Student student =
                findStudent(studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!");
        }

        String bookingId =
                readString(
                        "Mã đặt phòng: ");

        bookingService.cancelBooking(
                bookingId,
                student);

        System.out.println(
                "Hủy lịch đặt thành công!");
    }

    // ==============================
    // XEM BOOKING CỦA SINH VIÊN
    // ==============================

    private static void showMyBookings() {

        String studentId =
                readString(
                        "Nhập mã sinh viên: ");

        Student student =
                findStudent(studentId);

        if (student == null) {

            throw new IllegalArgumentException(
                    "Sinh viên không tồn tại!");
        }

        List<Booking> result =
                bookingService
                        .getBookingsByStudent(
                                student);

        if (result.isEmpty()) {

            System.out.println(
                    "Sinh viên chưa có lịch đặt.");

            return;
        }

        System.out.println(
                "\n===== LỊCH ĐẶT CỦA SINH VIÊN =====");

        for (Booking booking : result) {

            System.out.println(booking);
        }
    }

    // ==============================
    // XEM TẤT CẢ BOOKING
    // ==============================

    private static void showAllBookings() {

        if (bookings.isEmpty()) {

            System.out.println(
                    "Chưa có lịch đặt.");

            return;
        }

        System.out.println(
                "\n===== TẤT CẢ LỊCH ĐẶT =====");

        for (Booking booking : bookings) {

            System.out.println(booking);
        }
    }

    // ==============================
    // LƯU FILE
    // ==============================

    private static void saveData()
            throws Exception {

        StudentRepository studentRepository =
                new StudentRepository();

        RoomRepository roomRepository =
                new RoomRepository();

        BookingRepository bookingRepository =
                new BookingRepository();

        studentRepository.save(students);
        roomRepository.save(rooms);
        bookingRepository.save(bookings);

        System.out.println(
                "Lưu dữ liệu thành công!");
    }

    // ==============================
    // ĐỌC FILE
    // ==============================

    private static void loadData()
            throws Exception {

        StudentRepository studentRepository =
                new StudentRepository();

        RoomRepository roomRepository =
                new RoomRepository();

        BookingRepository bookingRepository =
                new BookingRepository();

        students.clear();
        rooms.clear();
        bookings.clear();

        students.addAll(
                studentRepository.load());

        rooms.addAll(
                roomRepository.load());

        bookings.addAll(
                bookingRepository.load(
                        students,
                        rooms));

        System.out.println(
                "Đọc dữ liệu thành công!");
    }

    // ==============================
    // TÌM SINH VIÊN
    // ==============================

    private static Student findStudent(
            String id) {

        for (Student student : students) {

            if (student.getStudentId()
                    .equalsIgnoreCase(id)) {

                return student;
            }
        }

        return null;
    }

    // ==============================
    // TÌM PHÒNG
    // ==============================

    private static Room findRoomById(
            String id) {

        for (Room room : rooms) {

            if (room.getRoomId()
                    .equalsIgnoreCase(id)) {

                return room;
            }
        }

        return null;
    }

    // ==============================
    // NHẬP STRING
    // ==============================

    private static String readString(
            String message) {

        System.out.print(message);

        return scanner.nextLine()
                .trim();
    }

    // ==============================
    // NHẬP INT
    // ==============================

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Vui lòng nhập số!");
            }
        }
    }
}