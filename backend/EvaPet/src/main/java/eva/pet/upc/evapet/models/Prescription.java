package eva.pet.upc.evapet.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrescription;

    @Column(name = "id_user_patient", nullable = false)
    private int idUserPatient;

    @Column(name = "id_eva", nullable = false)
    private int idEva;

    @Column(name = "diagnosis", length = 100)
    private String diagnosis;

    @Column(name = "date")
    private LocalDate date;

    public Prescription() {
    }

    public int getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(int idPrescription) {
        this.idPrescription = idPrescription;
    }

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
