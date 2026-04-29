package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface IMedicalServiceInterface {

    public List<MedicalHistory> ListAll();
    public Optional<MedicalHistory> ListById(Long id);
    public Optional<MedicalHistory> ListByIdDoctor(Long id);
    public Optional<MedicalHistory> ListByIdPatient(Long id);

    public MedicalHistory Insert(MedicalHistory m);
    public MedicalHistory Update(MedicalHistory m);
    public void Delete(Long id);

}
