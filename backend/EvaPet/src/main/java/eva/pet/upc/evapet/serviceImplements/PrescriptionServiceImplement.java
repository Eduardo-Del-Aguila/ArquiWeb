package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Prescription;
import eva.pet.upc.evapet.repositories.IPrescriptionRepository;
import eva.pet.upc.evapet.serviceInterfaces.IPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImplement implements IPrescriptionService {

    @Autowired
    private IPrescriptionRepository pRepo;


    @Override
    public List<Prescription> list() {
        return pRepo.findAll();
    }

    @Override
    public Prescription insert(Prescription pre) {
        return pRepo.save(pre);
    }

    @Override
    public Optional<Prescription> listId(int id) {
        return pRepo.findById(id);
    }

    @Override
    public void update(Prescription p) {
        pRepo.save(p);
    }

    @Override
    public void delete(int id) {
        pRepo.deleteById(id);
    }

    @Override
    public List<Object[]> RecipesPerPatient() {
        return pRepo.RecipesPerPatient();
    }
}
