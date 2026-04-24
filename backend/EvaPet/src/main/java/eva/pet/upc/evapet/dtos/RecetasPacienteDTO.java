package eva.pet.upc.evapet.dtos;

public class RecetasPacienteDTO {

    private int idUserPatient;
    private int totalRecetas;

    public int getIdUserPatient() {
        return idUserPatient;
    }

    public void setIdUserPatient(int idUserPatient) {
        this.idUserPatient = idUserPatient;
    }

    public int getTotalRecetas() {
        return totalRecetas;
    }

    public void setTotalRecetas(int totalRecetas) {
        this.totalRecetas = totalRecetas;
    }
}
