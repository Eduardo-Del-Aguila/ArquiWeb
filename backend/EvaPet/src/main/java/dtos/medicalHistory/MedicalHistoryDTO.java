package dtos.medicalHistory;

import lombok.Data;

@Data
public class MedicalHistoryDTO {
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
