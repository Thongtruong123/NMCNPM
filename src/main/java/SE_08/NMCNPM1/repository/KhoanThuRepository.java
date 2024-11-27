package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Khoanthu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoanThuRepository extends JpaRepository<Khoanthu, Integer> {

}