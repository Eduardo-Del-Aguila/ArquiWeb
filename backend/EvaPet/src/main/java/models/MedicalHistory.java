package com.example.tablascarlos.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medical_history")
@Data
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_medical_history;

    private String reason;
    private String register_at;
    private String treatment;
    private String diagnosis;
    private String observations;
    private String state;

    private int id_user_paciente;
    private int id_user_doctor;
    private int id_hospital;
    private int id_eva;
}
