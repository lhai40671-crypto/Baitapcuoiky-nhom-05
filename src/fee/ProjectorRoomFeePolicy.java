package fee;

public class ProjectorRoomFeePolicy
        implements RoomFeePolicy {

    private static final double PRICE_PER_HOUR = 20000;

    @Override
    public double calculateFee(double hours) {

        return hours * PRICE_PER_HOUR;
    }
}