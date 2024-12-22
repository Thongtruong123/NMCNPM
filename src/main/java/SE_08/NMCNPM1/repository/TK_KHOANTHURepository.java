package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.TK_KHOANTHU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface TK_KHOANTHURepository extends JpaRepository<TK_KHOANTHU, Integer> {
    @Query(value = "SELECT * FROM THONGKE_BY_KHOANTHU", nativeQuery = true)
    List <TK_KHOANTHU> findByFee_id(@Param("fee_id") int fee_id);
}
