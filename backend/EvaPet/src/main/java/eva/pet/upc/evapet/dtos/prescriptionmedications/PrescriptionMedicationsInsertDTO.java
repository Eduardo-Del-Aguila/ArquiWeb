package eva.pet.upc.evapet.dtos.prescriptionmedications;

public class PrescriptionMedicationsInsertDTO {
    private int idPrescriptionMedications;
    private int dose;
    private int frequency;
    private int duration;
    private int idPrescription;
    private int idMedication;

    public int getIdPrescriptionMedications() {
        return idPrescriptionMedications;
    }

    public void setIdPrescriptionMedications(int idPrescriptionMedications) {
        this.idPrescriptionMedications = idPrescriptionMedications;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(int idPrescription) {
        this.idPrescription = idPrescription;
    }

    public int getIdMedication() {
        return idMedication;
    }

    public void setIdMedication(int idMedication) {
        this.idMedication = idMedication;
    }
}
