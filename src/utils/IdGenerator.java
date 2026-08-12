package utils;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {
    }

    public static String generateBookingId() {

        return "BK-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}