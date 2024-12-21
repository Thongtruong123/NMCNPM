package SE_08.NMCNPM1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NhankhauId implements Serializable {
    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "hoten")
    private String hoten;

    public NhankhauId() {}

    public NhankhauId(String roomNumber, String hoten) {
        this.roomNumber = roomNumber;
        this.hoten = hoten;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhankhauId that = (NhankhauId) o;
        return Objects.equals(roomNumber, that.roomNumber) &&
                Objects.equals(hoten, that.hoten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, hoten);
    }
}
