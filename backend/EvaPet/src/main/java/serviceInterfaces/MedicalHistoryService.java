package com.example.tablascarlos.serviceInterfaces;

import com.example.tablascarlos.dtos.medicalHistory.MedicalHistoryDTO;
import com.example.tablascarlos.models.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {

    public String insertar(MedicalHistoryDTO dto);

    public List<MedicalHistory> listar();
}
