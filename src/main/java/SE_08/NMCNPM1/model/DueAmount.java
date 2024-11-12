package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "due_amounts")
public class DueAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid; // Đổi tên thành isPaid để chỉ trạng thái thanh toán

    // Mối quan hệ Many-to-One với Family
    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false) // Khóa ngoại trỏ đến bảng families
    @JsonIgnore // Thêm @JsonIgnore nếu gặp lỗi vòng lặp JSON
    private Family family;

    // Mối quan hệ Many-to-One với Invoice (nếu muốn gắn khoản thu vào hóa đơn khi được thanh toán)
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = true) // Khóa ngoại trỏ đến bảng invoices, có thể là null
    @JsonIgnore
    private Invoice invoice;

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
