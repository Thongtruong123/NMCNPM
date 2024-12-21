package SE_08.NMCNPM1.repository;

import SE_08.NMCNPM1.model.Nhankhau;
import SE_08.NMCNPM1.model.NhankhauId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NhanKhauRepository extends PagingAndSortingRepository<Nhankhau, NhankhauId> {

    @Query("SELECT n FROM Nhankhau n WHERE CONCAT(COALESCE(n.id.roomNumber, ''), ' ', COALESCE(n.id.hoten, ''), ' ', COALESCE(n.gioitinh, ''), ' ', COALESCE(n.ngaysinh, ''), ' ', COALESCE(n.vaitro, ''), ' ', COALESCE(n.sodienthoai, ''), ' ', COALESCE(n.quequan, ''), ' ', COALESCE(n.thuongtru, ''), ' ', COALESCE(n.tamtru, '')) LIKE %?1%")
    Page<Nhankhau> search(@Param("keyword") String keyword, Pageable pageable);

    // Sửa phương thức tìm kiếm theo khóa composite
    @Query("SELECT n FROM Nhankhau n WHERE n.id.roomNumber = :roomNumber AND n.id.hoten = :hoten")
    Optional<Nhankhau> findByRoomNumberAndHoten(@Param("roomNumber") String roomNumber, @Param("hoten") String hoten);

    void save(Nhankhau nhankhau);

    @Modifying
    @Query("DELETE FROM Nhankhau n WHERE n.id.roomNumber = :roomNumber AND n.id.hoten = :hoten")
    void deleteByRoomNumberAndHoten(@Param("roomNumber") String roomNumber, @Param("hoten") String hoten);

    List<Nhankhau> findAll(Sort sort);

    Optional<Nhankhau> findById(NhankhauId id);

    void deleteById(NhankhauId id);
}
