package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.MedicalHistory;
import eva.pet.upc.evapet.repositories.IMedicalHistoryRepository;
import eva.pet.upc.evapet.serviceInterfaces.IMedicalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalServiceImplements implements IMedicalServiceInterface {
    @Autowired
    private IMedicalHistoryRepository mR;

    @Override
    public List<MedicalHistory> ListAll() {
        return mR.findAll().stream()
                .filter(MedicalHistory::isActive)
                .toList();
    }

    @Override
    public Optional<MedicalHistory> ListById(Long id) {
        return mR.findById(id);
    }

    @Override
    public Optional<MedicalHistory> ListByIdDoctor(Long id) {
        return mR.findById(id);
    }

    @Override
    public Optional<MedicalHistory> ListByIdPatient(Long id) {
        return mR.findById(id);
    }

    @Override
    public MedicalHistory Insert(MedicalHistory m) {
        return mR.save(m);
    }

    @Override
    public MedicalHistory Update(MedicalHistory m) {
        return mR.save(m);
    }

    @Override
    public void Delete(Long id) {

    }
}
