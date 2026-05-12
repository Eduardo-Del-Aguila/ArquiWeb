package eva.pet.upc.evapet.dtos.medicalHistory;

import eva.pet.upc.evapet.enums.MedicalStatus;
import lombok.Data;

@Data
public class MedicalHistoryShowDTO {
    private String reason;
    private String treatment;
    private String observations;
    private String diagnostics;
    private MedicalStatus status;



}
