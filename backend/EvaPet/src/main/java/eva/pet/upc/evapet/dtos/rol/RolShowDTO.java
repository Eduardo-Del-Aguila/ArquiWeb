package eva.pet.upc.evapet.dtos.rol;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eva.pet.upc.evapet.enums.UserRol;
import jakarta.persistence.*;
import lombok.Data;

@JsonPropertyOrder({ "idRol", "nameRol", "descriptionRol" })
@Data
public class RolShowDTO {
    private Long idRol;
    private UserRol nameRol;
    private String descriptionRol;
}
