package fee;

public class SeminarRoomFeePolicy
        implements RoomFeePolicy {

    private static final double PRICE_PER_HOUR = 50000;

    @Override
    public double calculateFee(double hours) {

        return hours * PRICE_PER_HOUR;
    }
}