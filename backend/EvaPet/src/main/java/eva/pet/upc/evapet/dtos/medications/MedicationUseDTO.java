package eva.pet.upc.evapet.dtos.medications;

public class MedicationUseDTO {

    private String name;
    private int totalUso;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalUso() {
        return totalUso;
    }

    public void setTotalUso(int totalUso) {
        this.totalUso = totalUso;
    }
}
