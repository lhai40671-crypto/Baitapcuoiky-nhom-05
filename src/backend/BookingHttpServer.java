package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import exception.BookingException;
import model.Booking;
import model.Room;
import model.Student;
import repository.StudentRepository;
import service.BookingService;
import service.RoomService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingHttpServer {

    private static final String WEB_INDEX_PATH = "web/index.html";

    private final BookingService bookingService;
    private final RoomService roomService;
    private final StudentRepository studentRepository;
    private HttpServer server;

    public BookingHttpServer(BookingService bookingService,
                             RoomService roomService,
                             StudentRepository studentRepository) {

        this.bookingService = bookingService;
        this.roomService = roomService;
        this.studentRepository = studentRepository;
    }

    public void start(int port) throws IOException {

        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", this::handleIndex);
        server.createContext("/rooms", this::handleRooms);
        server.createContext("/students", this::handleStudents);
        server.createContext("/bookings", this::handleBookings);
        server.createContext("/bookings/cancel", this::handleCancelBooking);

        server.setExecutor(null);
        server.start();

        System.out.println("BookingHttpServer dang chay tai http://localhost:" + port);
        System.out.println("Mo trinh duyet vao http://localhost:" + port + "/ de dung giao dien web");
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    // GET / -> phuc vu trang web/index.html
    private void handleIndex(HttpExchange exchange) throws IOException {

        if (!"/".equals(exchange.getRequestURI().getPath())) {
            sendJson(exchange, 404, errorJson("Khong tim thay duong dan nay"));
            return;
        }

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, errorJson("Chi ho tro GET"));
            return;
        }

        Path indexPath = Path.of(WEB_INDEX_PATH);

        if (!Files.exists(indexPath)) {
            sendJson(exchange, 500, errorJson(
                    "Khong tim thay file " + WEB_INDEX_PATH
                            + " - tao thu muc web/ o goc project va dat index.html vao do"));
            return;
        }

        byte[] html = Files.readAllBytes(indexPath);

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, html.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(html);
        }
    }

    private void handleRooms(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, errorJson("Chi ho tro GET"));
            return;
        }

        List<Room> rooms = roomService.getAllRooms();

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < rooms.size(); i++) {

            Room room = rooms.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{")
                    .append("\"roomId\":\"").append(escape(room.getRoomId())).append("\",")
                    .append("\"roomName\":\"").append(escape(room.getRoomName())).append("\",")
                    .append("\"floor\":").append(room.getFloor()).append(",")
                    .append("\"capacity\":").append(room.getCapacity()).append(",")
                    .append("\"status\":\"").append(escape(room.getStatus())).append("\"")
                    .append("}");
        }

        json.append("]");

        sendJson(exchange, 200, json.toString());
    }

    private void handleStudents(HttpExchange exchange) throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, errorJson("Chi ho tro GET"));
            return;
        }

        List<Student> students = studentRepository.getAll();

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < students.size(); i++) {

            Student student = students.get(i);

            if (i > 0) {
                json.append(",");
            }

            json.append("{")
                    .append("\"studentId\":\"").append(escape(student.getUserId())).append("\",")
                    .append("\"name\":\"").append(escape(student.getName())).append("\",")
                    .append("\"className\":\"").append(escape(student.getClassName())).append("\",")
                    .append("\"phone\":\"").append(escape(student.getPhone())).append("\",")
                    .append("\"email\":\"").append(escape(student.getEmail())).append("\"")
                    .append("}");
        }

        json.append("]");

        sendJson(exchange, 200, json.toString());
    }

    private void handleBookings(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            handleGetBookings(exchange);
        } else if ("POST".equalsIgnoreCase(method)) {
            handleCreateBooking(exchange);
        } else {
            sendJson(exchange, 405, errorJson("Chi ho tro GET hoac POST"));
        }
    }

    private void handleGetBookings(HttpExchange exchange) throws IOException {

        Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
        String studentId = params.get("studentId");

        if (studentId == null || studentId.isBlank()) {
            sendJson(exchange, 400, errorJson("Thieu tham so studentId"));
            return;
        }

        List<Booking> bookings = bookingService.getBookingsByStudent(studentId);

        sendJson(exchange, 200, bookingsToJson(bookings));
    }

    private void handleCreateBooking(HttpExchange exchange) throws IOException {

        try {
            Map<String, String> body = parseFormBody(exchange.getRequestBody());

            String studentId = body.get("studentId");
            String roomId = body.get("roomId");
            LocalDate date = LocalDate.parse(body.get("date"));
            LocalTime startTime = LocalTime.parse(body.get("startTime"));
            LocalTime endTime = LocalTime.parse(body.get("endTime"));
            int participantCount = Integer.parseInt(body.get("participantCount"));

            Booking booking = bookingService.createBooking(
                    studentId, roomId, date, startTime, endTime, participantCount);

            sendJson(exchange, 201, bookingToJson(booking));

        } catch (BookingException e) {
            sendJson(exchange, 400, errorJson(e.getMessage()));
        } catch (Exception e) {
            sendJson(exchange, 400, errorJson("Du lieu khong hop le: " + e.getMessage()));
        }
    }

    private void handleCancelBooking(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, errorJson("Chi ho tro POST"));
            return;
        }

        try {
            Map<String, String> body = parseFormBody(exchange.getRequestBody());

            String bookingId = body.get("bookingId");
            String studentId = body.get("studentId");

            bookingService.cancelBooking(bookingId, studentId);

            sendJson(exchange, 200, "{\"message\":\"Huy lich dat thanh cong\"}");

        } catch (BookingException e) {
            sendJson(exchange, 400, errorJson(e.getMessage()));
        } catch (Exception e) {
            sendJson(exchange, 400, errorJson("Du lieu khong hop le: " + e.getMessage()));
        }
    }

    private String bookingsToJson(List<Booking> bookings) {

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < bookings.size(); i++) {

            if (i > 0) {
                json.append(",");
            }

            json.append(bookingToJson(bookings.get(i)));
        }

        json.append("]");

        return json.toString();
    }

    private String bookingToJson(Booking booking) {

        return "{" +
                "\"bookingId\":\"" + escape(booking.getBookingId()) + "\"," +
                "\"studentId\":\"" + escape(booking.getStudent().getUserId()) + "\"," +
                "\"roomId\":\"" + escape(booking.getRoom().getRoomId()) + "\"," +
                "\"date\":\"" + booking.getTimeSlot().getDate() + "\"," +
                "\"startTime\":\"" + booking.getTimeSlot().getStartTime() + "\"," +
                "\"endTime\":\"" + booking.getTimeSlot().getEndTime() + "\"," +
                "\"participantCount\":" + booking.getParticipantCount() + "," +
                "\"status\":\"" + escape(booking.getStatus()) + "\"" +
                "}";
    }

    private String errorJson(String message) {
        return "{\"error\":\"" + escape(message) + "\"}";
    }

    private String escape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private Map<String, String> parseQuery(String query) {

        Map<String, String> result = new HashMap<>();

        if (query == null || query.isBlank()) {
            return result;
        }

        for (String pair : query.split("&")) {

            String[] kv = pair.split("=", 2);

            if (kv.length == 2) {
                result.put(decode(kv[0]), decode(kv[1]));
            }
        }

        return result;
    }

    private Map<String, String> parseFormBody(InputStream inputStream) throws IOException {

        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        return parseQuery(body);
    }

    private String decode(String value) {
        return java.net.URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
