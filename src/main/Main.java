package main;

import backend.BookingHttpServer;
import exception.BookingException;
import model.Booking;
import model.Room;
import model.Student;
import repository.BookingRepository;
import repository.RoomRepository;
import repository.StudentRepository;
import service.BookingService;
import service.FeeService;
import service.RoomService;
import utils.CurrencyUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();
        RoomRepository roomRepository = new RoomRepository();
        BookingRepository bookingRepository =
                new BookingRepository(studentRepository, roomRepository);

        RoomService roomService = new RoomService(roomRepository);
        BookingService bookingService =
                new BookingService(studentRepository, roomRepository, bookingRepository);
        FeeService feeService = new FeeService();

        startHttpServer(bookingService, roomService, studentRepository);

        while (true) {

            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showStudents(studentRepository);
                case "2" -> showRooms(roomService);
                case "3" -> bookRoom(bookingService);
                case "4" -> showMyBookings(bookingService, feeService);
                case "5" -> cancelBooking(bookingService);
                case "0" -> {
                    System.out.println("Tam biet!");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le, thu lai.");
            }
        }
    }

    // Khởi động HTTP server song song với console, không làm gián đoạn menu
    // nếu khởi động thất bại (ví dụ port 8080 đang bị chiếm).
    private static void startHttpServer(BookingService bookingService,
                                        RoomService roomService,
                                        StudentRepository studentRepository) {
        try {
            BookingHttpServer httpServer =
                    new BookingHttpServer(bookingService, roomService, studentRepository);
            httpServer.start(8080);
        } catch (Exception e) {
            System.out.println("Khong the khoi dong HTTP server: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("""

                ===== HE THONG DAT PHONG SEMINAR =====
                1. Danh sach sinh vien
                2. Danh sach phong
                3. Dat phong
                4. Xem lich dat phong cua toi
                5. Huy dat phong
                0. Thoat
                Chon: """);
    }

    private static void showStudents(StudentRepository studentRepository) {

        List<Student> students = studentRepository.getAll();

        if (students.isEmpty()) {
            System.out.println("Chua co sinh vien nao trong he thong.");
            return;
        }

        students.forEach(System.out::println);
    }

    private static void showRooms(RoomService roomService) {

        List<Room> rooms = roomService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("Chua co phong nao trong he thong.");
            return;
        }

        rooms.forEach(System.out::println);
    }

    private static void bookRoom(BookingService bookingService) {

        try {
            System.out.print("Nhap ma sinh vien: ");
            String studentId = scanner.nextLine().trim();

            System.out.print("Nhap ma phong: ");
            String roomId = scanner.nextLine().trim();

            System.out.print("Nhap ngay (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Nhap gio bat dau (HH:mm): ");
            LocalTime startTime = LocalTime.parse(scanner.nextLine().trim());

            System.out.print("Nhap gio ket thuc (HH:mm): ");
            LocalTime endTime = LocalTime.parse(scanner.nextLine().trim());

            System.out.print("Nhap so nguoi tham gia: ");
            int participantCount = Integer.parseInt(scanner.nextLine().trim());

            Booking booking = bookingService.createBooking(
                    studentId, roomId, date, startTime, endTime, participantCount);

            System.out.println("Chi tiet: " + booking);

        } catch (DateTimeParseException e) {
            System.out.println("Dinh dang ngay/gio khong hop le!");
        } catch (NumberFormatException e) {
            System.out.println("So nguoi tham gia phai la so!");
        } catch (BookingException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }

    private static void showMyBookings(BookingService bookingService, FeeService feeService) {

        System.out.print("Nhap ma sinh vien: ");
        String studentId = scanner.nextLine().trim();

        List<Booking> bookings = bookingService.getBookingsByStudent(studentId);

        if (bookings.isEmpty()) {
            System.out.println("Ban chua co lich dat phong nao.");
            return;
        }

        for (Booking booking : bookings) {
            double fee = feeService.calculateFee(booking);
            System.out.println(booking + " | Phi: " + CurrencyUtils.formatVND(fee));
        }
    }

    private static void cancelBooking(BookingService bookingService) {

        try {
            System.out.print("Nhap ma sinh vien: ");
            String studentId = scanner.nextLine().trim();

            System.out.print("Nhap ma dat phong can huy: ");
            String bookingId = scanner.nextLine().trim();

            bookingService.cancelBooking(bookingId, studentId);

        } catch (BookingException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}