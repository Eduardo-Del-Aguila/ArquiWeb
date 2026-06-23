package eva.pet.upc.evapet.dtos.user;

import lombok.Data;

@Data
public class UsersInsertDTO {
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String image_url;
    private String phoneNumber;
    private Long rolId;


}
