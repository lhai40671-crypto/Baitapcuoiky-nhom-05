package fee;

public class FreeRoomFeePolicy
        implements RoomFeePolicy {

    @Override
    public double calculateFee(double hours) {

        return 0;
    }
}