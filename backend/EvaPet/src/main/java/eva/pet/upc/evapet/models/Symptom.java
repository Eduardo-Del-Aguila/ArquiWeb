package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.Severity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
    @Table(name = "symptom")
    public class Symptom {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idSymptom;

        @Column(name = "name", length = 20, nullable = false)
        private String name;

        @Column(name = "severity", length = 100, nullable = false)
        @Enumerated(EnumType.STRING)
        private Severity severity;

        @ManyToOne
        @JoinColumn(name = "id_medical_history")
        private MedicalHistory idMedicalHistory;

}




