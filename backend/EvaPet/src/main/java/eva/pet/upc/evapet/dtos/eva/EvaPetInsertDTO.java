package eva.pet.upc.evapet.dtos.eva;

import eva.pet.upc.evapet.enums.StatusPet;
import lombok.Data;

@Data
public class EvaPetInsertDTO {
    private String name;
    private String description;
    private StatusPet status;




}
