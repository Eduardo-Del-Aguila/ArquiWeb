package eva.pet.upc.evapet.dtos.prescription;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({
        "idPrescription",
        "idUserPatient",
        "idEva",
        "diagnosis",
        "date"
})
public class PrescriptionShowDTO {

    private int idPrescription;
    private int idUserPatient;
    private int idEva;
    private String diagnosis;
    private LocalDate date;

}