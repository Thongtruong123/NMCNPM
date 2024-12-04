package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Khoanthu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoanThuRepository extends JpaRepository<Khoanthu, Integer> {
    @Query("SELECT k FROM Khoanthu k WHERE CONCAT(k.id, ' ', k.tenkhoanthu, ' ', k.sotien, ' ', k.ngaytao, ' ', k.hanchot, ' ', k.batbuoc, ' ', k.nguoitao) LIKE %?1%")
    List<Khoanthu> search(String keyword);
}
