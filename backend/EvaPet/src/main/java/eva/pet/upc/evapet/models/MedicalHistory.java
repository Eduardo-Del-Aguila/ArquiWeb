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
    @Column(name = "diagnostics", nullable = true)
    private String  diagnostics;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicalStatus status;

    @Column(name = "isActive", nullable = false)
    private boolean isActive;
    @Column(name = "registerAt", nullable = false)
    private LocalDateTime registerAt;
    @Column(name = "updateAt", nullable = true)
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private User patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_eva")
    private EvaPet eva;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_doctor")
    private User doctor;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_hospital")
    private Hospital hospital;

}
