package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.Family;
import SE_08.NMCNPM1.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public Family findByRoomNumber(String roomNumber) {
        return familyRepository.findByRoomNumber(roomNumber);
    }
}
