package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.DueAmount;
import SE_08.NMCNPM1.model.DueAmountKey;
import SE_08.NMCNPM1.repository.DueAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service // Đánh dấu để Spring quản lý service này
public class DueAmountService {

    @Autowired // Dependency Injection để sử dụng repository
    private DueAmountRepository dueAmountRepository;

    // Phương thức tìm các khoản chưa thanh toán dựa trên roomNumber
    public List<DueAmount> findUnpaidByRoomNumber(String roomNumber) {
        return dueAmountRepository.findByRoomNumberAndInvoiceIdIsNull(roomNumber); // Gọi repository đúng cách
    }

    // Phương thức cập nhật khoản thu
    public void updateDueAmount(DueAmount dueAmount) {
        dueAmountRepository.save(dueAmount); // Gọi save từ instance của repository
    }

    // Phương thức tìm khoản thu dựa trên composite key
    public Optional<DueAmount> findById(DueAmountKey dueAmountKey) {
        return dueAmountRepository.findById(dueAmountKey); // Sử dụng phương thức findById của repository
    }
    // Tìm khoản thu dựa trên roomNumber và feeId
    public Optional<DueAmount> findByRoomNumberAndFeeId(String roomNumber, Long feeId) {
        return dueAmountRepository.findByRoomNumberAndFeeId(roomNumber, feeId);
    }

}
