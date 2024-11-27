package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Khoanthu;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KhoanthuService {

    Page<Khoanthu> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}


