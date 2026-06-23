package eva.pet.upc.evapet.dtos.eva;

import eva.pet.upc.evapet.enums.StatusPet;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class EvaPetShowDTO {
    private  Long idEva;
    private String name;
    private String description;
    private StatusPet status;
    private int level;
    private int experiencie;
}
