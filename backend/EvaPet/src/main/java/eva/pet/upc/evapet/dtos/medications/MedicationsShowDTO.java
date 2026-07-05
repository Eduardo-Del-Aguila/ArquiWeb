package eva.pet.upc.evapet.dtos.medications;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"idMedication","name","description","active"})
public class MedicationsShowDTO {

    private int idMedication;

    private String name;

    private String description;

    private boolean active;

}
