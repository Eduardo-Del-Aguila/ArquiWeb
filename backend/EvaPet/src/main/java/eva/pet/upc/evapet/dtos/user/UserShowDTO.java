package eva.pet.upc.evapet.dtos.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "name", "lastName", "mail", "image_url", "nameRol" })
public class UserShowDTO {
    private long idUser;
    private String name;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String image_url;
    private String nameRol;
}