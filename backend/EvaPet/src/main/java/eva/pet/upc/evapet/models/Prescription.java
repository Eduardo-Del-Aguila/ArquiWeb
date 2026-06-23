package eva.pet.upc.evapet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
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


}
