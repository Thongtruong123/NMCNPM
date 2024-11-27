//package SE_08.NMCNPM1.service;
//
//import SE_08.NMCNPM1.model.Khoanthu;
//import SE_08.NMCNPM1.repository.KhoanthuRepository;
//import org.hibernate.FetchNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//public class KhoanthuServiceImpl implements KhoanthuService{
//
//    @Autowired
//    private KhoanthuRepository khoanthuRepository;
//    @Override
//    public void saveKhoanthu(Khoanthu khoanthu) {
//        khoanthuRepository.save(khoanthu);
//    }
//
//    @Override
//    public Iterable<Khoanthu> findKhoanthu() {
//        return khoanthuRepository.findAll();
//    }
//
//    @Override
//    public Khoanthu getKhoanthuId(int id) {
//        Khoanthu khoanthu = khoanthuRepository.findById(id).orElseThrow(() -> new FetchNotFoundException("Khoanthu", id));
//        return khoanthu;
//    }
//
//    @Override
//    public void deleteEmployee(int id) {
//
//    }
//
//    @Override
//    public Page<Khoanthu> findPage(int pageNo, int pageSize, String sortBy) {
//        Sort sort =   Sort.by(Sort.Direction.ASC, sortBy);
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//        return khoanthuRepository.findAll(pageable ) ;
//
//    }
//}
