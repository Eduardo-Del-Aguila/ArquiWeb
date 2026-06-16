package eva.pet.upc.evapet.dtos.rol;

import eva.pet.upc.evapet.enums.UserRol;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RolShowDTO {
    private Long idRol;
    private UserRol nameRol;
    private String descriptionRol;
}
