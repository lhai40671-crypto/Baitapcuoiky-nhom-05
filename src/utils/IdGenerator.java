package utils;

public class IdGenerator {

    private static int studentNumber = 4;
    private static int bookingNumber = 3;

    public static String generateStudentId() {

        return String.format(
                "SV%03d",
                studentNumber++
        );
    }

    public static String generateBookingId() {

        return String.format(
                "BK%03d",
                bookingNumber++
        );
    }
}////git checkout phamduchung