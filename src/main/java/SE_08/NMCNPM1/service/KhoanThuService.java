package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhoanThuService {

    @Autowired
    private KhoanThuRepository khoanThuRepository;

    // Tìm thông tin KhoanThu theo ID
    public Khoanthu findById(int id) {
        Optional<Khoanthu> optionalKhoanThu = khoanThuRepository.findById(id);
        return optionalKhoanThu.orElse(null); // Trả về null nếu không tìm thấy
    }

    public Page<Khoanthu> listAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return khoanThuRepository.search(keyword, pageable);
        }
        // Nếu không có từ khóa, trả về danh sách theo phân trang và sắp xếp.
        return khoanThuRepository.findAll(pageable);
    }

    public void save(Khoanthu khoanthu) {
        khoanThuRepository.save(khoanthu);
    }

    public void deleteById(int id) {
        khoanThuRepository.deleteById(id);
    }
}