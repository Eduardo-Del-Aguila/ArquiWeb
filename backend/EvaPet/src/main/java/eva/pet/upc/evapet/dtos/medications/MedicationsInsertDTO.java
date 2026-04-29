package eva.pet.upc.evapet.dtos.medications;

public class MedicationsInsertDTO {
    private int idMedication;
    private String name;
    private String description;
    private boolean isActive;

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
