package SE_08.NMCNPM1.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "due_amounts")
@IdClass(DueAmountKey.class) // Đặt composite key
public class DueAmount implements Serializable {

    @Id
    @Column(name = "room_number", nullable = false)
    private String roomNumber; // Khóa chính thứ nhất

    @Id
    @Column(name = "fee_id", nullable = false)
    private int feeId; // Khóa chính thứ hai

    @Column(name = "invoice_id", nullable = true)
    private Integer invoiceId; // Liên kết tới hóa đơn

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number", insertable = false, updatable = false)
    private Family family;

    // Getters và Setters
    public int getFeeId() {
        return feeId;
    }

    public void setFeeId(int feeId) {
        this.feeId = feeId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}


