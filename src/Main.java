import model.Booking;
import model.Room;
import model.Student;

import service.BookingService;
import service.FeeService;
import service.RoomService;
import service.StudentService;

import utils.CurrencyUtils;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static StudentService studentService =
            new StudentService();

    static RoomService roomService =
            new RoomService();

    static BookingService bookingService =
            new BookingService();

    static FeeService feeService =
            new FeeService();

    public static void main(String[] args) {

        showMenu();
    }

    // ========================================
    // MENU CHINH
    // ========================================

    public static void showMenu() {

        while (true) {

            System.out.println();
            System.out.println(
                    "========== QUAN LY DAT PHONG =========="
            );

            System.out.println(
                    "1. Xem danh sach phong"
            );

            System.out.println(
                    "2. Tim phong"
            );

            System.out.println(
                    "3. Xem danh sach sinh vien"
            );

            System.out.println(
                    "4. Xem lich phong"
            );

            System.out.println(
                    "5. Dat phong"
            );

            System.out.println(
                    "6. Xem lich dat"
            );

            System.out.println(
                    "7. Huy lich dat"
            );

            System.out.println(
                    "8. Tinh phi phong"
            );

            System.out.println(
                    "0. Thoat"
            );

            System.out.println(
                    "======================================="
            );

            System.out.print(
                    "Nhap lua chon: "
            );

            String choice =
                    scanner.nextLine();

            switch (choice) {

                case "1":
                    showRooms();
                    break;

                case "2":
                    findRoom();
                    break;

                case "3":
                    showStudents();
                    break;

                case "4":
                    showRoomSchedule();
                    break;

                case "5":
                    createBooking();
                    break;

                case "6":
                    showBookings();
                    break;

                case "7":
                    cancelBooking();
                    break;

                case "8":
                    calculateFee();
                    break;

                case "0":

                    System.out.println(
                            "Cam on ban da su dung chuong trinh!"
                    );

                    return;

                default:

                    System.out.println(
                            "Lua chon khong hop le!"
                    );
            }
        }
    }

    // ========================================
    // 1. XEM DANH SACH PHONG
    // ========================================

    public static void showRooms() {

        System.out.println();
        System.out.println(
                "========== DANH SACH PHONG =========="
        );

        List<Room> rooms =
                roomService.getAllRooms();

        if (rooms.isEmpty()) {

            System.out.println(
                    "Khong co phong nao."
            );

            return;
        }

        for (Room room : rooms) {

            System.out.println(
                    "Ma phong: "
                            + room.getRoomId()
            );

            System.out.println(
                    "Ten phong: "
                            + room.getRoomName()
            );

            System.out.println(
                    "Tang: "
                            + room.getFloor()
            );

            System.out.println(
                    "Suc chua: "
                            + room.getCapacity()
            );

            System.out.println(
                    "Loai phong: "
                            + room.getRoomType()
            );

            System.out.println(
                    "Trang thai: "
                            + room.getStatus()
            );

            System.out.println(
                    "------------------------------------"
            );
        }
    }

    // ========================================
    // 2. TIM PHONG
    // ========================================

    public static void findRoom() {

        System.out.println();
        System.out.println(
                "========== TIM PHONG =========="
        );

        System.out.print(
                "Nhap ma phong: "
        );

        String roomId =
                scanner.nextLine();

        Room room =
                roomService.findRoomById(roomId);

        if (room == null) {

            System.out.println(
                    "Khong tim thay phong!"
            );

            return;
        }

        System.out.println();
        System.out.println(
                "========== THONG TIN PHONG =========="
        );

        System.out.println(
                "Ma phong: "
                        + room.getRoomId()
        );

        System.out.println(
                "Ten phong: "
                        + room.getRoomName()
        );

        System.out.println(
                "Tang: "
                        + room.getFloor()
        );

        System.out.println(
                "Suc chua: "
                        + room.getCapacity()
        );

        System.out.println(
                "Loai phong: "
                        + room.getRoomType()
        );

        System.out.println(
                "Trang thai: "
                        + room.getStatus()
        );
    }

    // ========================================
    // 3. XEM DANH SACH SINH VIEN
    // ========================================

    public static void showStudents() {

        System.out.println();
        System.out.println(
                "========== DANH SACH SINH VIEN =========="
        );

        List<Student> students =
                studentService.getAllStudents();

        if (students.isEmpty()) {

            System.out.println(
                    "Khong co sinh vien."
            );

            return;
        }

        for (Student student : students) {

            System.out.println(
                    "Ma sinh vien: "
                            + student.getStudentId()
            );

            System.out.println(
                    "Ho ten: "
                            + student.getFullName()
            );

            System.out.println(
                    "So dien thoai: "
                            + student.getPhone()
            );

            System.out.println(
                    "Lop: "
                            + student.getClassName()
            );

            System.out.println(
                    "Email: "
                            + student.getEmail()
            );

            System.out.println(
                    "------------------------------------"
            );
        }
    }

    // ========================================
    // 4. XEM LICH PHONG
    // ========================================

    public static void showRoomSchedule() {

        System.out.println();
        System.out.println(
                "========== XEM LICH PHONG =========="
        );

        System.out.print(
                "Nhap ma phong: "
        );

        String roomId =
                scanner.nextLine();

        Room room =
                roomService.findRoomById(roomId);

        if (room == null) {

            System.out.println(
                    "Khong tim thay phong!"
            );

            return;
        }

        System.out.print(
                "Nhap ngay (dd/MM/yyyy): "
        );

        String date =
                scanner.nextLine();

        List<Booking> bookings =
                bookingService
                        .getBookingsByRoomAndDate(
                                roomId,
                                date
                        );

        System.out.println();
        System.out.println(
                "========== LICH PHONG =========="
        );

        System.out.println(
                "Phong: "
                        + room.getRoomName()
        );

        System.out.println(
                "Ma phong: "
                        + room.getRoomId()
        );

        System.out.println(
                "Ngay: "
                        + date
        );

        System.out.println(
                "------------------------------------"
        );

        if (bookings.isEmpty()) {

            System.out.println(
                    "Phong dang trong ca ngay."
            );

            return;
        }

        System.out.println(
                "Cac khung gio da dat:"
        );

        for (Booking booking : bookings) {

            System.out.println(
                    booking.getStartTime()
                            + " - "
                            + booking.getEndTime()
                            + " : DA DAT"
            );
        }

        System.out.println(
                "------------------------------------"
        );
    }

    // ========================================
    // 5. DAT PHONG
    // ========================================

    public static void createBooking() {

        System.out.println();
        System.out.println(
                "========== DAT PHONG =========="
        );

        System.out.print(
                "Ma sinh vien: "
        );

        String studentId =
                scanner.nextLine();

        System.out.print(
                "Ma phong: "
        );

        String roomId =
                scanner.nextLine();

        System.out.print(
                "Ngay dat (dd/MM/yyyy): "
        );

        String date =
                scanner.nextLine();

        System.out.print(
                "Gio bat dau (HH:mm): "
        );

        String startTime =
                scanner.nextLine();

        System.out.print(
                "Gio ket thuc (HH:mm): "
        );

        String endTime =
                scanner.nextLine();

        System.out.print(
                "So nguoi: "
        );

        int numberOfPeople;

        try {

            numberOfPeople =
                    Integer.parseInt(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Loi: So nguoi phai la so!"
            );

            return;
        }

        bookingService.createBooking(
                studentId,
                roomId,
                date,
                startTime,
                endTime,
                numberOfPeople
        );
    }

    // ========================================
    // 6. XEM LICH DAT
    // ========================================

    public static void showBookings() {

        System.out.println();
        System.out.println(
                "========== XEM LICH DAT =========="
        );

        System.out.print(
                "Nhap ma sinh vien: "
        );

        String studentId =
                scanner.nextLine();

        Student student =
                studentService.findStudentById(
                        studentId
                );

        if (student == null) {

            System.out.println(
                    "Sinh vien khong ton tai!"
            );

            return;
        }

        List<Booking> bookings =
                bookingService.getBookingsByStudent(
                        studentId
                );

        if (bookings.isEmpty()) {

            System.out.println(
                    "Sinh vien chua co lich dat."
            );

            return;
        }

        for (Booking booking : bookings) {

            System.out.println(
                    "Ma dat phong: "
                            + booking.getBookingId()
            );

            System.out.println(
                    "Ma phong: "
                            + booking.getRoomId()
            );

            System.out.println(
                    "Ngay: "
                            + booking.getDate()
            );

            System.out.println(
                    "Thoi gian: "
                            + booking.getStartTime()
                            + " - "
                            + booking.getEndTime()
            );

            System.out.println(
                    "So nguoi: "
                            + booking.getNumberOfPeople()
            );

            System.out.println(
                    "Trang thai: "
                            + booking.getStatus()
            );

            System.out.println(
                    "------------------------------------"
            );
        }
    }

    // ========================================
    // 7. HUY LICH DAT
    // ========================================

    public static void cancelBooking() {

        System.out.println();
        System.out.println(
                "========== HUY LICH DAT =========="
        );

        System.out.print(
                "Nhap ma dat phong: "
        );

        String bookingId =
                scanner.nextLine();

        System.out.print(
                "Nhap ma sinh vien: "
        );

        String studentId =
                scanner.nextLine();

        bookingService.cancelBooking(
                bookingId,
                studentId
        );
    }

    // ========================================
    // 8. TINH PHI PHONG
    // ========================================

    public static void calculateFee() {

        System.out.println();
        System.out.println(
                "========== TINH PHI PHONG =========="
        );

        System.out.print(
                "Nhap ma phong: "
        );

        String roomId =
                scanner.nextLine();

        Room room =
                roomService.findRoomById(roomId);

        if (room == null) {

            System.out.println(
                    "Khong tim thay phong!"
            );

            return;
        }

        System.out.print(
                "Nhap so gio: "
        );

        double hours;

        try {

            hours =
                    Double.parseDouble(
                            scanner.nextLine()
                    );

        } catch (NumberFormatException e) {

            System.out.println(
                    "Loi: So gio khong hop le!"
            );

            return;
        }

        try {

            double fee =
                    feeService.calculateFee(
                            room,
                            hours
                    );

            System.out.println(
                    "Loai phong: "
                            + room.getRoomType()
            );

            System.out.println(
                    "So gio: "
                            + hours
            );

            System.out.println(
                    "Tien phong: "
                            + CurrencyUtils.formatVND(
                            fee
                    )
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Loi: "
                            + e.getMessage()
            );
        }
    }
}