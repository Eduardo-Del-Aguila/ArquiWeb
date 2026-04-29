package eva.pet.upc.evapet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "medications")
public class Medications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMedication;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 100, nullable = true)
    private String description;

    @Column(name = "is_active", nullable = true)
    private boolean isActive;

    public Medications() {
    }

    public Medications(int idMedication, String name, String description, boolean isActive) {
        this.idMedication = idMedication;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    public int getIdMedication() {
        return idMedication;
    }

    public void setIdMedication(int idMedication) {
        this.idMedication = idMedication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
