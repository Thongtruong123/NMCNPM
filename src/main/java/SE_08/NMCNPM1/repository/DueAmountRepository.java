package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.DueAmount;
import SE_08.NMCNPM1.model.DueAmountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DueAmountRepository extends JpaRepository<DueAmount, DueAmountKey> {

    // Tìm khoản thu dựa trên room_number và fee_id
    Optional<DueAmount> findByRoomNumberAndFeeId(String roomNumber, Long feeId);

    // Tìm tất cả các khoản thu chưa thanh toán của một phòng
    List<DueAmount> findByRoomNumberAndInvoiceIdIsNull(String roomNumber);
}
