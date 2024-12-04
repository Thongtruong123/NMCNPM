package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Khoanthu> listAll(String keyword, Sort sort) {
        if (keyword != null && !keyword.isEmpty()) {
            return khoanThuRepository.search(keyword);
        }
        if (sort != null) {
            return khoanThuRepository.findAll(sort);
        }
        return khoanThuRepository.findAll();
    }


    public void save(Khoanthu khoanthu) {
        khoanThuRepository.save(khoanthu);
    }

    public void deleteById(int id) {
        khoanThuRepository.deleteById(id);
    }
}