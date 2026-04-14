package eva.pet.upc.evapet.models;

import jakarta.persistence.*;

import java.time.LocalDate;

    @Entity
    @Table(name = "Specialty")
    public class Specialty {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idSpecialty;

        @Column(name = "nameSpecialty", length = 20, nullable = false)
        private String nameSpecialty;

        @Column(name = "description", length = 100, nullable = false)
        private String description;

        public Specialty() {
        }

        public Specialty(int idSpecialty, String nameSpecialty, String description) {
            this.idSpecialty = idSpecialty;
            this.nameSpecialty = nameSpecialty;
            this.description = description;
        }

        public int getIdSpecialty() {
            return idSpecialty;
        }

        public void setIdSpecialty(int idSpecialty) {
            this.idSpecialty = idSpecialty;
        }

        public String getNameSpecialty() {
            return nameSpecialty;
        }

        public void setNameSpecialty(String nameSpecialty) {
            this.nameSpecialty = nameSpecialty;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }




