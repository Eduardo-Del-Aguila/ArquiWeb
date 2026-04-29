package eva.pet.upc.evapet.dtos;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String description;
    private String emotion;

}
