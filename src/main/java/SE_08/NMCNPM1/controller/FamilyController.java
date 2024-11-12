package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Family;
import SE_08.NMCNPM1.model.Invoice;
import SE_08.NMCNPM1.service.FamilyService;
import SE_08.NMCNPM1.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@RestController
@RequestMapping("/api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private InvoiceService invoiceService;

    // API để lấy thông tin hộ gia đình theo số phòng
    @GetMapping("/{roomNumber}")
    public ResponseEntity<Family> getFamilyByRoomNumber(@PathVariable String roomNumber) {
        Family family = familyService.findByRoomNumber(roomNumber);
        if (family != null) {
            return ResponseEntity.ok(family);
        } else {
            // Trả về 404 nếu không tìm thấy hộ gia đình
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API để tạo hóa đơn
    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        if (invoice == null || invoice.getRoomNumber() == null || invoice.getTotalAmount() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Dữ liệu hóa đơn không hợp lệ hoặc bị thiếu.");
        }
        // Lấy tên người dùng từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Đây là tên người dùng đã đăng nhập

        invoice.setCreatedBy(username); // Thiết lập tên người dùng vào trường createdBy
        try {
            Invoice savedInvoice = invoiceService.saveInvoice(invoice);
            return ResponseEntity.ok(savedInvoice);
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu hóa đơn: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi máy chủ. Không thể lưu hóa đơn.");
        }
    }

}
