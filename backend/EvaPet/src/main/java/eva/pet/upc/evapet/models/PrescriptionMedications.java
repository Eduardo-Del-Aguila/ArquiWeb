package eva.pet.upc.evapet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "prescription_medications")
public class PrescriptionMedications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrescriptionMedications;

    @Column(name = "dose")
    private int dose;

    @Column(name = "frequency")
    private int frequency;

    @Column(name = "duration")
    private int duration;

    @Column(name = "idPrescription", nullable = false)
    private int idPrescription;

    @Column(name = "idMedication", nullable = false)
    private int idMedication;


}
