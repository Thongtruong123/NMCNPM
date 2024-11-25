package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.DueAmount;
import SE_08.NMCNPM1.model.Invoice;
import SE_08.NMCNPM1.repository.DueAmountRepository;
import SE_08.NMCNPM1.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DueAmountRepository dueAmountRepository;

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        // 1. Lưu hóa đơn trước để tạo `invoice_id`
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 2. Cập nhật `invoice_id` cho các khoản thu đã chọn
        List<DueAmount> selectedDues = invoice.getSelectedDueAmounts();
        if (selectedDues != null && !selectedDues.isEmpty()) {
            for (DueAmount due : selectedDues) {
                if (due.getRoomNumber() != null && due.getFeeId() != null) {
                    // Lấy khoản thu từ database dựa trên room_number và fee_id
                    DueAmount managedDue = dueAmountRepository.findByRoomNumberAndFeeId(due.getRoomNumber(), due.getFeeId())
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy khoản thu với room_number: "
                                    + due.getRoomNumber() + ", fee_id: " + due.getFeeId()));

                    // Cập nhật `invoice_id`
                    managedDue.setInvoiceId(savedInvoice.getId());
                    dueAmountRepository.save(managedDue);
                }
            }
        }

        return savedInvoice; // Trả về hóa đơn đã lưu
    }
}

