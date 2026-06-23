package eva.pet.upc.evapet.dtos.user;

import eva.pet.upc.evapet.enums.UserRol;
import lombok.Data;

@Data
public class UserUpdateSImple {
    private String name;
    private String lastName;
    private String phoneNumber;
    private UserRol nameRol;

}
