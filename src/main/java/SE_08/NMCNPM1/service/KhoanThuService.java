package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.KhoanThu;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KhoanThuService {

    @Autowired
    private KhoanThuRepository khoanThuRepository;

    // Tìm thông tin KhoanThu theo ID
    public KhoanThu findById(Long id) {
        Optional<KhoanThu> optionalKhoanThu = khoanThuRepository.findById(Math.toIntExact(id));
        return optionalKhoanThu.orElse(null); // Trả về null nếu không tìm thấy
    }
}
