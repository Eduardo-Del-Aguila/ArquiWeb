package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryInsertDTO;
import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryShowDTO;
import eva.pet.upc.evapet.models.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface IMedicalServiceInterface {

    public List<MedicalHistory> ListAll();
    public Optional<MedicalHistory> ListById(Long id);
    public Optional<MedicalHistory> ListByIdDoctor(Long id);
    public Optional<MedicalHistory> ListByIdPatient(Long id);

    public MedicalHistory insert(MedicalHistoryInsertDTO m, Long evaId, Long patientId);
    public MedicalHistory update(Long id, MedicalHistoryShowDTO m);
    public void delete(Long id);

}
