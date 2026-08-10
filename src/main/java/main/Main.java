package main;

import model.*;
import repository.BookingRepository;
import repository.RoomRepository;
import service.BookingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Student> students = new ArrayList<>();
    static List<Room> rooms = new ArrayList<>();

    static BookingService bookingService = new BookingService();
    static RoomRepository roomRepository = new RoomRepository();
    static BookingRepository bookingRepository = new BookingRepository();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        createData();

        roomRepository.save(rooms);

        bookingService.loadBookings(
                bookingRepository.load(students, rooms)
        );

        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ ĐẶT PHÒNG =====");
            System.out.println("1. Xem danh sách phòng");
            System.out.println("2. Tìm phòng");
            System.out.println("3. Đặt phòng");
            System.out.println("4. Xem lịch đặt");
            System.out.println("5. Hủy lịch");
            System.out.println("6. Tính phí phòng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> showRooms();
                case 2 -> searchRoom();
                case 3 -> bookRoom();
                case 4 -> showBookings();
                case 5 -> cancelBooking();
                case 6 -> calculateFee();
                case 0 -> System.out.println("Đã thoát!");
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 0);
    }

    static void createData() {

        students.add(
                new Student(
                        "SV01",
                        "Nguyen Van A",
                        "0123456789",
                        "CNTT01",
                        "a@gmail.com"
                )
        );

        rooms.add(
                new NormalRoom(
                        "P01",
                        "Phòng thường 01",
                        1,
                        10,
                        "Đang hoạt động"
                )
        );

        rooms.add(
                new ProjectorRoom(
                        "P02",
                        "Phòng máy chiếu 01",
                        2,
                        20,
                        "Đang hoạt động"
                )
        );

        rooms.add(
                new SeminarRoom(
                        "P03",
                        "Phòng seminar 01",
                        3,
                        30,
                        "Đang hoạt động"
                )
        );
    }

    static void showRooms() {

        System.out.println("\n===== DANH SÁCH PHÒNG =====");

        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    static void searchRoom() {

        System.out.print("Nhập mã phòng hoặc loại phòng: ");
        String key = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Room room : rooms) {

            if (room.getRoomId().toLowerCase().contains(key)
                    || room.getType().toLowerCase().contains(key)) {

                System.out.println(room);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy phòng!");
        }
    }

    static void bookRoom() {

        try {

            System.out.print("Mã đặt phòng: ");
            String id = sc.nextLine();

            System.out.print("Mã sinh viên: ");
            String studentId = sc.nextLine();

            Student student = findStudent(studentId);

            System.out.print("Mã phòng: ");
            String roomId = sc.nextLine();

            Room room = findRoom(roomId);

            System.out.print("Ngày (yyyy-MM-dd): ");
            String date = sc.nextLine();

            System.out.print("Giờ bắt đầu (HH:mm): ");
            String start = sc.nextLine();

            System.out.print("Giờ kết thúc (HH:mm): ");
            String end = sc.nextLine();

            System.out.print("Số người: ");
            int people = Integer.parseInt(sc.nextLine());

            Booking booking = new Booking(
                    id,
                    student,
                    room,
                    date,
                    start,
                    end,
                    people
            );

            bookingService.bookRoom(
                    booking,
                    students,
                    rooms
            );

            bookingRepository.save(
                    bookingService.getBookings()
            );

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void showBookings() {

        System.out.println("\n===== DANH SÁCH LỊCH ĐẶT =====");

        for (Booking booking : bookingService.getBookings()) {
            System.out.println(booking);
        }
    }

    static void cancelBooking() {

        try {

            System.out.print("Mã đặt phòng: ");
            String bookingId = sc.nextLine();

            System.out.print("Mã sinh viên: ");
            String studentId = sc.nextLine();

            Student student = findStudent(studentId);

            bookingService.cancelBooking(
                    bookingId,
                    student
            );

            bookingRepository.save(
                    bookingService.getBookings()
            );

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void calculateFee() {

        try {

            System.out.print("Mã phòng: ");
            String roomId = sc.nextLine();

            Room room = findRoom(roomId);

            System.out.print("Số giờ sử dụng: ");
            int hours = Integer.parseInt(sc.nextLine());

            if (room instanceof RoomFeePolicy) {

                RoomFeePolicy policy =
                        (RoomFeePolicy) room;

                double fee =
                        policy.calculateFee(hours);

                System.out.println(
                        "Chi phí: " + fee + " VNĐ"
                );

            } else {

                System.out.println(
                        "Phòng này không có chính sách tính phí!"
                );
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static Student findStudent(String id) {

        for (Student student : students) {

            if (student.getId().equals(id)) {
                return student;
            }
        }

        throw new IllegalArgumentException(
                "Không tìm thấy sinh viên!"
        );
    }

    static Room findRoom(String id) {

        for (Room room : rooms) {

            if (room.getRoomId().equals(id)) {
                return room;
            }
        }

        throw new IllegalArgumentException(
                "Không tìm thấy phòng!"
        );
    }
}