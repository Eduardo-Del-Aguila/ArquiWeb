package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.MedicalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "medical_history")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para la tabla insert
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "treatment", nullable = false)
    private String treatment;
    @Column(name = "observations", nullable = false)
    private String observations;
    @Column(name = "diagnostics", nullable = false)
    private String  diagnostics;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicalStatus status;


    @Column(name = "isActive", nullable = false)
    private boolean isActive;
    @Column(name = "registerAt", nullable = false)
    private LocalDateTime registerAt;
    @Column(name = "update", nullable = false)
    private LocalDateTime updateAt;


    @OneToOne
    private User idPatient;

    @OneToOne
    private User idDoctor;

    @OneToOne
    private User idHospital;

    @OneToOne(optional = false)
    private EvaPet idEva;


}
