package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.PrescriptionMedications;

import java.util.List;
import java.util.Optional;

public interface IPrescriptionMedicationsService {
    public List<PrescriptionMedications> list();

    PrescriptionMedications insert(PrescriptionMedications premed);

    Optional<PrescriptionMedications> listId(int id);

    void update(PrescriptionMedications pm);

    void delete(int id);

    List<Object[]> MostUsedMedications();
}
