package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.*;
import SE_08.NMCNPM1.service.DueAmountService;
import SE_08.NMCNPM1.service.FamilyService;
import SE_08.NMCNPM1.service.InvoiceService;
import SE_08.NMCNPM1.service.KhoanThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DueAmountService dueAmountService;

    @Autowired
    private KhoanThuService khoanThuService; // Service để lấy thông tin KhoanThu

    // API để lấy thông tin hộ gia đình theo số phòng
    @GetMapping("/{roomNumber}")
    public ResponseEntity<?> getFamilyByRoomNumber(@PathVariable String roomNumber) {
        try {
            // Lấy thông tin hộ gia đình
            Family family = familyService.findByRoomNumber(roomNumber);
            if (family == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Không tìm thấy hộ gia đình."));
            }

            // Lấy các khoản chưa đóng từ bảng due_amounts
            List<DueAmount> dueAmounts = dueAmountService.findUnpaidByRoomNumber(roomNumber);

            // Tăng cường dữ liệu (thêm tên và số tiền từ bảng khoan_thu)
            List<Map<String, Object>> enrichedDueAmounts = dueAmounts.stream().map(due -> {
                Khoanthu khoanThu = khoanThuService.findById(due.getFeeId());
                Map<String, Object> dueDetails = new HashMap<>();
                dueDetails.put("roomNumber", due.getRoomNumber());
                dueDetails.put("feeId", due.getFeeId());
                dueDetails.put("invoiceId", due.getInvoiceId());
                dueDetails.put("name", khoanThu != null ? khoanThu.getTenkhoanthu() : "Không xác định");
                dueDetails.put("amount", khoanThu != null ? khoanThu.getSotien() : 0);
                return dueDetails;
            }).collect(Collectors.toList());

            // Đưa thông tin hộ gia đình và các khoản chưa đóng vào phản hồi
            Map<String, Object> response = new HashMap<>();
            response.put("family", family);
            response.put("dueAmounts", enrichedDueAmounts);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi truy xuất dữ liệu: " + e.getMessage()));
        }
    }

    // API để tạo hóa đơn
    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        try {
            // Lấy thông tin người dùng đăng nhập
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // Lấy tên người dùng đăng nhập

            // Kiểm tra dữ liệu hóa đơn
            if (invoice == null || invoice.getRoomNumber() == null || invoice.getRoomNumber().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "roomNumber không hợp lệ."));
            }

            if (invoice.getSelectedDueAmounts() == null || invoice.getSelectedDueAmounts().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Danh sách khoản thu không được để trống."));
            }

            // Gán roomNumber nếu bị thiếu trong DueAmount
            for (DueAmount due : invoice.getSelectedDueAmounts()) {
                if (due.getRoomNumber() == null || due.getRoomNumber().isEmpty()) {
                    due.setRoomNumber(invoice.getRoomNumber());
                }
            }

            // Gán giá trị `createdBy` từ tài khoản đang đăng nhập
            invoice.setCreatedBy(username);
            invoice.setCreatedAt(LocalDateTime.now());

            // Lưu hóa đơn và cập nhật các khoản thu
            Invoice savedInvoice = invoiceService.saveInvoice(invoice);
            for (DueAmount due : invoice.getSelectedDueAmounts()) {
                due.setInvoiceId(savedInvoice.getId());
                dueAmountService.updateDueAmount(due);
            }

            // Tăng cường thông tin cho selectedDueAmounts
            List<Map<String, Object>> detailedDueAmounts = invoice.getSelectedDueAmounts().stream().map(due -> {
                // Lấy thông tin từ bảng KhoanThu
                Khoanthu khoanThu = khoanThuService.findById(due.getFeeId());
                Map<String, Object> dueDetails = new HashMap<>();
                dueDetails.put("feeId", due.getFeeId());
                dueDetails.put("invoiceId", due.getInvoiceId());
                dueDetails.put("roomNumber", due.getRoomNumber());
                dueDetails.put("name", khoanThu != null ? khoanThu.getTenkhoanthu() : "Không xác định");
                dueDetails.put("amount", khoanThu != null ? khoanThu.getSotien() : 0);
                return dueDetails;
            }).toList();

            // Tạo phản hồi trả về
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedInvoice.getId());
            response.put("payerName", savedInvoice.getPayerName());
            response.put("phoneNumber", savedInvoice.getPhoneNumber());
            response.put("roomNumber", savedInvoice.getRoomNumber());
            response.put("createdBy", savedInvoice.getCreatedBy());
            response.put("createdAt", savedInvoice.getCreatedAt());
            response.put("totalAmount", savedInvoice.getTotalAmount());
            response.put("selectedDueAmounts", detailedDueAmounts);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Dữ liệu không hợp lệ: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi tạo hóa đơn: " + e.getMessage()));
        }
    }
}
