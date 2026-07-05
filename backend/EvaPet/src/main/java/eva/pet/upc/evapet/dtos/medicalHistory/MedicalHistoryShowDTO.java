package eva.pet.upc.evapet.dtos.medicalHistory;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eva.pet.upc.evapet.enums.MedicalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "id","reason", "treatment", "observations", "diagnostics", "status" })
public class MedicalHistoryShowDTO {
    private Long id;
    private String reason;
    private String treatment;
    private String observations;
    private String diagnostics;
    private MedicalStatus status;
    private String evaName;
    private String patientName;
    private String doctorName;
    private String nameHospital;
    private LocalDateTime registerAt;
}
