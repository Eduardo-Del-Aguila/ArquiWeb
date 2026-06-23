package eva.pet.upc.evapet.dtos.medicalHistory;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eva.pet.upc.evapet.enums.MedicalStatus;
import lombok.Data;

@Data
@JsonPropertyOrder({ "reason", "treatment", "observations", "diagnostics", "status" })
public class MedicalHistoryShowDTO {
    private String reason;
    private String treatment;
    private String observations;
    private String diagnostics;
    private MedicalStatus status;
    
}
