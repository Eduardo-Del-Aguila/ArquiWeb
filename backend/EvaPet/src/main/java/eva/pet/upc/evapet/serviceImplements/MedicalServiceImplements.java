package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryInsertDTO;
import eva.pet.upc.evapet.enums.MedicalStatus;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.models.MedicalHistory;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IEvaPetRepository;
import eva.pet.upc.evapet.repositories.IHospitalRepository;
import eva.pet.upc.evapet.repositories.IMedicalHistoryRepository;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceInterfaces.IMedicalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalServiceImplements implements IMedicalServiceInterface {
    @Autowired
    private IMedicalHistoryRepository mR;

    @Autowired
    private IUsersRepository uR;

    @Autowired
    private IEvaPetRepository eR;

    @Autowired
    private IHospitalRepository hR;


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
    public MedicalHistory insert(MedicalHistoryInsertDTO dto, Long evaId, Long hospitalId) {
        EvaPet eva = eR.findById(evaId)
                .orElseThrow(() -> new RuntimeException("Eva Pet no encontrada con id: " + evaId));
        Long patientId = eva.getPatient().getId();

        Hospital hospital = hR.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + hospitalId));

        User patient = uR.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + patientId));

        MedicalHistory history = new MedicalHistory();
        history.setReason(dto.getReason());
        history.setTreatment(dto.getTreatment());
        history.setObservations(dto.getObservations());
        history.setDiagnostics(dto.getDiagnostics());
        history.setStatus(dto.getStatus() != null ? dto.getStatus() : MedicalStatus.PENDING);
        history.setActive(true);
        history.setRegisterAt(LocalDateTime.now());
        history.setEva(eva);
        history.setPatient(patient);
        history.setDoctor(null);
        history.setHospital(hospital);


        return mR.save(history);
    }

    @Override
    public MedicalHistory update(Long id, MedicalHistoryInsertDTO dto) {
        MedicalHistory history = mR.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial médico no encontrado con id: " + id));

        if (dto.getReason() != null)      history.setReason(dto.getReason());
        if (dto.getTreatment() != null)   history.setTreatment(dto.getTreatment());
        if (dto.getObservations() != null) history.setObservations(dto.getObservations());
        if (dto.getDiagnostics() != null) history.setDiagnostics(dto.getDiagnostics());
        if (dto.getStatus() != null)      history.setStatus(dto.getStatus());
        history.setUpdateAt(LocalDateTime.now());

        return mR.save(history);
    }

    @Override
    public void delete(Long id) {
        MedicalHistory history = mR.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial médico no encontrado con id: " + id));
        // Soft delete
        history.setActive(false);
        history.setUpdateAt(LocalDateTime.now());
        mR.save(history);
    }
}
