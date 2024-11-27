package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.repository.KhoanthuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class KhoanthuServiceImp implements KhoanthuService{

    @Autowired
    private KhoanthuRepository khoanthuRepository;
        @Override
        public Page<Khoanthu> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
            Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            return this.khoanthuRepository.findAll(pageable);
        }

}
