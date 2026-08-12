import exception.BookingException;
import model.Booking;
import model.Room;
import model.Student;
import model.TimeSlot;
import repository.BookingRepository;
import repository.RoomRepository;
import repository.StudentRepository;
import service.BookingService;
import service.RoomService;
import service.StudentService;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository(Path.of("data/students.txt"));

        RoomRepository roomRepository = new RoomRepository(Path.of("data/rooms.txt"));

        BookingRepository bookingRepository = new BookingRepository(Path.of("data/bookings.txt"));

        StudentService studentService = new StudentService(studentRepository);

        RoomService roomService = new RoomService(roomRepository);

        BookingService bookingService = new BookingService(studentRepository, roomRepository,bookingRepository);

        while (true) {

            showMenu();

            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        showStudents(studentService);
                        break;

                    case "2":
                        showRooms(roomService);
                        break;

                    case "3":
                        bookRoom(bookingService);
                        break;

                    case "4":
                        showMyBookings(bookingService);
                        break;

                    case "5":
                        cancelBooking(bookingService);
                        break;

                    case "0":
                        System.out.println("Đã thoát chương trình.");
                        return;

                    default:
                        System.out.println("❌ Lựa chọn không hợp lệ.");
                }

            } catch (BookingException e) {

                System.out.println("❌ Lỗi nghiệp vụ: "
                        + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println("❌ Dữ liệu không hợp lệ: "
                        + e.getMessage());

            } catch (Exception e) {

                System.out.println("❌ Có lỗi xảy ra: "
                        + e.getMessage());
            }
        }
    }


    private static void showMenu() {

        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     QUẢN LÝ ĐẶT PHÒNG HỌC NHÓM       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Xem danh sách sinh viên           ║");
        System.out.println("║ 2. Xem danh sách phòng                ║");
        System.out.println("║ 3. Đặt phòng                          ║");
        System.out.println("║ 4. Xem lịch đặt phòng                 ║");
        System.out.println("║ 5. Hủy lịch đặt                       ║");
        System.out.println("║ 0. Thoát                              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print(" Chọn chức năng: ");
    }


    private static void showStudents(StudentService service) {

        System.out.println();
        System.out.println("===== DANH SÁCH SINH VIÊN =====");

        for (Student student : service.getAllStudents()) {
            System.out.println(student);
        }
    }


    private static void showRooms(RoomService service) {

        System.out.println();
        System.out.println("===== DANH SÁCH PHÒNG =====");

        for (Room room : service.getAllRooms()) {
            System.out.println(room);
        }
    }


    private static void bookRoom(BookingService service) {

        System.out.println();
        System.out.println("===== ĐẶT PHÒNG =====");

        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();

        System.out.print("Mã phòng: ");
        String roomId = scanner.nextLine();

        System.out.print("Ngày đặt (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Giờ bắt đầu: ");
        int startHour = Integer.parseInt(scanner.nextLine());

        System.out.print("Giờ kết thúc: ");
        int endHour = Integer.parseInt(scanner.nextLine());

        System.out.print("Số người: ");
        int people = Integer.parseInt(scanner.nextLine());

        LocalDateTime start =
                LocalDateTime.of(
                        java.time.LocalDate.parse(date),
                        java.time.LocalTime.of(startHour, 0)
                );

        LocalDateTime end =
                LocalDateTime.of(
                        java.time.LocalDate.parse(date),
                        java.time.LocalTime.of(endHour, 0)
                );

        TimeSlot timeSlot =
                new TimeSlot(start, end);

        Booking booking =
                service.book(
                        studentId,
                        roomId,
                        timeSlot,
                        people
                );

        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          ĐẶT PHÒNG THÀNH CÔNG        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println(booking);
    }


    private static void showMyBookings(BookingService service) {

        System.out.println();
        System.out.println("===== LỊCH ĐẶT PHÒNG =====");

        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();

        var bookings =
                service.getMyBookings(studentId);

        if (bookings.isEmpty()) {

            System.out.println("Chưa có lịch đặt phòng.");

            return;
        }

        bookings.forEach(System.out::println);
    }


    private static void cancelBooking(BookingService service) {

        System.out.println();
        System.out.println("===== HỦY LỊCH ĐẶT =====");

        System.out.print("Mã đặt phòng: ");
        String bookingId = scanner.nextLine();

        System.out.print("Mã sinh viên: ");
        String studentId = scanner.nextLine();

        service.cancel(
                bookingId,
                studentId
        );

        System.out.println(" Hủy lịch thành công.");
    }
}