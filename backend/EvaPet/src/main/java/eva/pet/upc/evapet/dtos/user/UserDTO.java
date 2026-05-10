package eva.pet.upc.evapet.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class UserDTO {
    private String name;
    private String lastName;
    private String image_url;
}
