package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.DueAmount;
import SE_08.NMCNPM1.model.Invoice;
import SE_08.NMCNPM1.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        List<DueAmount> managedDues = new ArrayList<>();
        for (DueAmount due : invoice.getSelectedDueAmounts()) {
            if (due.getId() != null) { // Nếu DueAmount đã tồn tại (có ID)
                DueAmount managedDue = entityManager.find(DueAmount.class, due.getId());
                managedDues.add(managedDue); // Nạp từ database để được quản lý
            } else {
                managedDues.add(due); // Nếu là mới, cứ thêm vào bình thường
            }
        }
        invoice.setSelectedDueAmounts(managedDues);
        return invoiceRepository.save(invoice); // Lưu invoice cùng với các dueAmounts
    }
}
