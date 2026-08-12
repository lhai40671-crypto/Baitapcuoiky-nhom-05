package utils;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isEmpty(
            String value) {

        return value == null
                || value.trim().isEmpty();
    }

    public static boolean isValidPhone(
            String phone) {

        return phone != null
                && phone.matches("\\d{10,11}");
    }

    public static boolean isValidEmail(
            String email) {

        return email != null
                && email.matches(
                "^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isPositive(
            int number) {

        return number > 0;
    }
}