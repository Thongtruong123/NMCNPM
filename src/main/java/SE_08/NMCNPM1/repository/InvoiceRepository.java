package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
