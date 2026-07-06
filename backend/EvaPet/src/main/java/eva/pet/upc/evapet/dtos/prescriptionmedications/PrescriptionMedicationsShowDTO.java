package eva.pet.upc.evapet.dtos.prescriptionmedications;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "idPrescriptionMedications",
        "dose",
        "frequency",
        "duration",
        "idPrescription",
        "idMedication"
})
public class PrescriptionMedicationsShowDTO {

    private int idPrescriptionMedications;
    private int dose;
    private int frequency;
    private int duration;
    private int idPrescription;
    private int idMedication;

}