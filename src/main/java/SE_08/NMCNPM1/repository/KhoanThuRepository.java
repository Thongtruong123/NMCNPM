package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Khoanthu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhoanThuRepository extends PagingAndSortingRepository<Khoanthu, Integer> {
    @Query("SELECT k FROM Khoanthu k WHERE CONCAT(COALESCE(k.id, ''), ' ', COALESCE(k.tenkhoanthu, ''), ' ', COALESCE(k.sotien, ''), ' ', COALESCE(k.ngaytao, ''), ' ', COALESCE(k.hanchot, ''), ' ', COALESCE(k.batbuoc, ''), ' ', COALESCE(k.nguoitao, '')) LIKE %?1%")

    Page<Khoanthu> search(@Param("keyword") String keyword, Pageable pageable);

    Optional<Khoanthu> findById(int id);

    void save(Khoanthu khoanthu);

    void deleteById(int id);
}
