package eva.pet.upc.evapet.dtos.prescription;

import java.time.LocalDate;

public class PrescriptionDTO {
    private int idUserPatient;
    private int idEva;
    private String diagnosis;
    private LocalDate date;

    public int getIdUserPatient() {
        return idUserPatient;
    }

    public void setIdUserPatient(int idUserPatient) {
        this.idUserPatient = idUserPatient;
    }

    public int getIdEva() {
        return idEva;
    }

    public void setIdEva(int idEva) {
        this.idEva = idEva;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
