package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Khoanthu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhoanthuRepository extends JpaRepository<Khoanthu, Integer> {
    List<Khoanthu> findAll();
    Optional<Khoanthu> findById(int id);
}
