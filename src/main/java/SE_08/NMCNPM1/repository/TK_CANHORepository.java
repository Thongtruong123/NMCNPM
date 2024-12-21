package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.TK_CANHO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TK_CANHORepository extends JpaRepository<TK_CANHO, TK_CANHO.TKCANHOKey> {
    @Query(value = "SELECT * FROM THONGKE_BY_CANHO WHERE fee_id = ?1", nativeQuery = true)
    List<TK_CANHO> findByFee_id(@Param("fee_id") int fee_id);

    @Query(value = "SELECT * FROM THONGKE_BY_CANHO WHERE room_number = ?1 ORDER BY hanchot DESC", nativeQuery = true)
    List<TK_CANHO> findByRoom_numberOrderByHanchotDesc(@Param("room_number") String room_number);
}
