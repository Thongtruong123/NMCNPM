package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findByRoomNumber(String roomNumber);
}
