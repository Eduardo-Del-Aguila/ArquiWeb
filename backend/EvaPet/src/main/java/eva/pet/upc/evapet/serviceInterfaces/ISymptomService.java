package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Symptom;

import java.util.List;
import java.util.Optional;

public interface ISymptomService {
    public List<Symptom> List();
    public Optional<Symptom> ListById(Long id);
    public Symptom insert(Symptom s);
    public void update(Symptom s);
    public void delete(Long id);
    List<Object[]> countSymptomsBySeverity();
    List<Object[]> symptomsPerMedicalHistory();
}
