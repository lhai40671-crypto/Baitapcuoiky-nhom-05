package model;

public class BookingValidatorImpl implements BookingValidator {

    @Override
    public void validate(Booking booking) {

        if (booking == null) {
            throw new IllegalArgumentException("Lịch đặt không tồn tại!");
        }

        if (booking.getStudent() == null) {
            throw new IllegalArgumentException("Sinh viên không tồn tại!");
        }

        if (booking.getRoom() == null) {
            throw new IllegalArgumentException("Phòng không tồn tại!");
        }

        if (!booking.getRoom().isAvailable()) {
            throw new IllegalArgumentException("Phòng đang bảo trì!");
        }

        if (booking.getStartTime() == null ||
                booking.getEndTime() == null) {
            throw new IllegalArgumentException("Thời gian không hợp lệ!");
        }

        if (booking.getEndTime().compareTo(booking.getStartTime()) <= 0) {
            throw new IllegalArgumentException(
                    "Thời gian kết thúc phải lớn hơn thời gian bắt đầu!"
            );
        }

        if (booking.getNumberOfPeople() <= 0) {
            throw new IllegalArgumentException(
                    "Số lượng người phải lớn hơn 0!"
            );
        }

        if (booking.getNumberOfPeople()
                > booking.getRoom().getCapacity()) {
            throw new IllegalArgumentException(
                    "Số lượng người vượt quá sức chứa phòng!"
            );
        }
    }
}