package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Long feeId; // Khóa chính thứ hai

    @Column(name = "invoice_id", nullable = true)
    private Long invoiceId; // Liên kết tới hóa đơn

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number", insertable = false, updatable = false)
    private Family family;

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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
}


