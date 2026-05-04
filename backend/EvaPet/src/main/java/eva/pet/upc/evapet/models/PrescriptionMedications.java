package eva.pet.upc.evapet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "prescription_medications")
public class PrescriptionMedications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrescriptionMedications;

    @Column(name = "dose", length = 50)
    private int dose;
 
    @Column(name = "frequency", length = 50)
    private int frequency;

    @Column(name = "duration", length = 50)
    private int duration;

    @Column(name = "idPrescription", nullable = false)
    private int idPrescription;

    @Column(name = "idMedication", nullable = false)
    private int idMedication;

    public PrescriptionMedications() {
    }

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
