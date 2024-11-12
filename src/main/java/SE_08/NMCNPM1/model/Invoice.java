package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "owner_name", nullable = true) // Tùy chọn, có thể là null
    private String ownerName;

    @Column(name = "phone_number", nullable = true) // Tùy chọn, có thể là null
    private String phoneNumber;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    // Mối quan hệ One-to-Many với DueAmount
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")  // Khóa ngoại trong bảng due_amounts trỏ đến bảng invoices
    private List<DueAmount> selectedDueAmounts;

    // Getters và Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<DueAmount> getSelectedDueAmounts() {
        return selectedDueAmounts;
    }

    public void setSelectedDueAmounts(List<DueAmount> selectedDueAmounts) {
        this.selectedDueAmounts = selectedDueAmounts;
    }
    // Phương thức này sẽ tự động được gọi trước khi lưu vào cơ sở dữ liệu
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Đặt thời gian hiện tại cho createdAt
    }
}
