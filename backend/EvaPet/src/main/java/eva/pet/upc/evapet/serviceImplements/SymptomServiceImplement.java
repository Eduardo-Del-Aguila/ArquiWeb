package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Symptom;
import eva.pet.upc.evapet.repositories.ISymptomRepository;
import eva.pet.upc.evapet.serviceInterfaces.ISymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SymptomServiceImplement implements ISymptomService {
    @Autowired
    private ISymptomRepository sR;

    @Override
    public List<Symptom> List() {
        return sR.findAll();
    }

    @Override
    public Optional<Symptom> ListById(Long id) {
        return sR.findById(id);
    }

    @Override
    public Symptom insert(Symptom s) {
        return sR.save(s);
    }

    @Override
    public void update(Symptom s) {
        sR.save(s);
    }

    @Override
    public void delete(Long id) {
        sR.deleteById(id);
    }

    @Override
    public List<Object[]> countSymptomsBySeverity() {
        return sR.countSymptomsBySeverity();
    }

    @Override
    public List<Object[]> symptomsPerMedicalHistory() {
        return sR.symptomsPerMedicalHistory();
    }
}
