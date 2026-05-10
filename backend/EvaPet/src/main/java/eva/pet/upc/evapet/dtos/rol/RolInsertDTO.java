package eva.pet.upc.evapet.dtos.rol;

import eva.pet.upc.evapet.enums.UserRol;
import lombok.Data;

@Data
public class RolInsertDTO {
    private Long idRol; 
    private UserRol nameRol;
    private String description;

}

