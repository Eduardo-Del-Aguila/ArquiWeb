package eva.pet.upc.evapet.dtos.medicalHistory;

import eva.pet.upc.evapet.enums.MedicalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalHistoryInsertDTO {
    private String reason;
}
