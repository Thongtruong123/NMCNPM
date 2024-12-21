package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Nhankhau;
import SE_08.NMCNPM1.model.NhankhauId;
import SE_08.NMCNPM1.repository.NhanKhauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NhanKhauService {

    @Autowired
    private NhanKhauRepository nhankhauRepository;

    public Nhankhau findByRoomnumberAndHoten(String room_number, String hoten) {
        NhankhauId id = new NhankhauId(room_number, hoten);
        Optional<Nhankhau> optionalNhankhau = nhankhauRepository.findById(id);
        return optionalNhankhau.orElse(null);
    }

    public Page<Nhankhau> listAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return nhankhauRepository.search(keyword, pageable);
        }
        return nhankhauRepository.findAll(pageable);
    }

    public void save(Nhankhau nhankhau) {
        nhankhauRepository.save(nhankhau);
    }

    public void deleteByRoomNumberAndHoten(String room_number, String hoten) {
        NhankhauId id = new NhankhauId(room_number, hoten);
        nhankhauRepository.deleteById(id);
    }

}
