package SE_08.NMCNPM1.model;

import java.io.Serializable;
import java.util.Objects;

public class DueAmountKey implements Serializable {

    private String roomNumber;
    private Long feeId;

    // Constructors
    public DueAmountKey() {}

    public DueAmountKey(String roomNumber, Long feeId) {
        this.roomNumber = roomNumber;
        this.feeId = feeId;
    }

    // Getters và Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    // Override equals() và hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DueAmountKey that = (DueAmountKey) o;
        return Objects.equals(roomNumber, that.roomNumber) && Objects.equals(feeId, that.feeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, feeId);
    }

    // toString()
    @Override
    public String toString() {
        return "DueAmountKey{" +
                "roomNumber='" + roomNumber + '\'' +
                ", feeId=" + feeId +
                '}';
    }
}
