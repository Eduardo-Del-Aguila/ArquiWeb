package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.Severity;
import jakarta.persistence.*;

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

//        @ManyToOne
//        @JoinColumn(name = "id_medical_history")
//
//        private

        public Symptom() {
        }

        public Symptom(Long idSymptom, String name, String description) {
            this.idSymptom = idSymptom;
            this.name = name;
            this.severity = severity;
        }

        public Long getIdSymptom() {
            return idSymptom;
        }

        public void setIdSymptom(Long idSymptom) {
            this.idSymptom = idSymptom;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Severity getSeverity() {
            return severity;
        }

        public void setSeverity(Severity severity) {
            this.severity = severity;
        }
}




