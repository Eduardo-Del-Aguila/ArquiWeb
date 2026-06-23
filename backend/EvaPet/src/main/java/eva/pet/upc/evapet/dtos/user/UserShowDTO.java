package eva.pet.upc.evapet.dtos.user;

import lombok.Data;

@Data
public class UserShowDTO {
    private String name ;
    private String lastName ;
    private String  mail;
    private String  image_url;
    private String  nameRol;
}
