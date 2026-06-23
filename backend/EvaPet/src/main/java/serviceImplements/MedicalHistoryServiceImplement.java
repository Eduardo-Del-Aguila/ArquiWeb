package com.example.tablascarlos.serviceImplements;

import com.example.tablascarlos.dtos.medicalHistory.MedicalHistoryDTO;
import com.example.tablascarlos.models.MedicalHistory;
import com.example.tablascarlos.repositories.MedicalHistoryRepository;
import com.example.tablascarlos.serviceInterfaces.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryServiceImplement implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository repository;

    @Override
    public String insertar(MedicalHistoryDTO dto) {

        MedicalHistory m = new MedicalHistory();

        m.setReason(dto.getReason());
        m.setRegister_at(dto.getRegister_at());
        m.setTreatment(dto.getTreatment());
        m.setDiagnosis(dto.getDiagnosis());
        m.setObservations(dto.getObservations());
        m.setState(dto.getState());

        m.setId_user_paciente(dto.getId_user_paciente());
        m.setId_user_doctor(dto.getId_user_doctor());
        m.setId_hospital(dto.getId_hospital());
        m.setId_eva(dto.getId_eva());

        repository.save(m);

        return "Historial médico registrado";
    }
    @Override
    public List<MedicalHistory> listar() {
        return repository.findAll();
    }
}