package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.PrescriptionMedications;
import eva.pet.upc.evapet.repositories.IPrescriptionMedicationsRepository;
import eva.pet.upc.evapet.serviceInterfaces.IPrescriptionMedicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionMedicationsServiceImplement implements IPrescriptionMedicationsService {
    @Autowired
    private IPrescriptionMedicationsRepository pmRepo;

    @Override
    public List<PrescriptionMedications> list() {
        return pmRepo.findAll();
    }

    @Override
    public PrescriptionMedications insert(PrescriptionMedications premed) {
        return pmRepo.save(premed);
    }

    @Override
    public Optional<PrescriptionMedications> listId(int id) {
        return pmRepo.findById(id);
    }

    @Override
    public void update(PrescriptionMedications pm) {
        pmRepo.save(pm);
    }

    @Override
    public void delete(int id) {
        pmRepo.deleteById(id);
    }

    @Override
    public List<Object[]> MostUsedMedications() {
        return pmRepo.MostUsedMedications();
    }
}
