package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Medications;
import eva.pet.upc.evapet.repositories.IMedicationsRepository;
import eva.pet.upc.evapet.serviceInterfaces.IMedicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationsServiceImplement implements IMedicationsService {
    @Autowired
    private IMedicationsRepository mRepo;

    @Override
    public List<Medications> list()
    {
        return mRepo.findAll();
    }

    @Override
    public Medications insert(Medications med) {
        return mRepo.save(med);
    }

    @Override
    public Optional<Medications> listId(int id) {
        return mRepo.findById(id);
    }

    @Override
    public void update(Medications m) {
        mRepo.save(m);
    }

    @Override
    public void delete(int id) {
        mRepo.deleteById(id);

    }

    @Override
    public boolean existsByName(String name) {
        return mRepo.existsByNameIgnoreCase(name);
    }

    @Override
    public List<Medications> listarMedicamentosActivos() {
        return mRepo.listarMedicamentosActivos();
    }
}
