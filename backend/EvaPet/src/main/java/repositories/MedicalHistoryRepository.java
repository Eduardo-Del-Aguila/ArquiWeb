package com.example.tablascarlos.repositories;

import com.example.tablascarlos.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

    @Query("SELECT m FROM MedicalHistory m WHERE m.idUserPaciente = :idPaciente")
    List<MedicalHistory> findByPaciente(Integer idPaciente);

    @Query("SELECT m FROM MedicalHistory m WHERE m.idUserDoctor = :idDoctor")
    List<MedicalHistory> findByDoctor(Integer idDoctor);

    @Query("SELECT m FROM MedicalHistory m WHERE m.idHospital = :idHospital")
    List<MedicalHistory> findByHospital(Integer idHospital);
}