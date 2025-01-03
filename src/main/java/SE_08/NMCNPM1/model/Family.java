package SE_08.NMCNPM1.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "families")
public class Family {

    @Id
    @Column(name = "room_number", nullable = false)
    private String roomNumber; // room_number là khóa chính

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // Các khoản nợ
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DueAmount> dueAmounts;

    // Getters và Setters

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<DueAmount> getDueAmounts() {
        return dueAmounts;
    }

    public void setDueAmounts(List<DueAmount> dueAmounts) {
        this.dueAmounts = dueAmounts;
    }
}
