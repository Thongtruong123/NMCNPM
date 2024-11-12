package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.DueAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DueAmountRepository extends JpaRepository<DueAmount, Long> {
}
