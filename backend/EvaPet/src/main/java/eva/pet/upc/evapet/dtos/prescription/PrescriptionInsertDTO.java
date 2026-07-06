package eva.pet.upc.evapet.dtos.prescription;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionInsertDTO {

    private int idPrescription;
    private int idUserPatient;
    private int idEva;
    private String diagnosis;
    private LocalDate date;

}