package eva.pet.upc.evapet.dtos.rol;

import eva.pet.upc.evapet.enums.UserRol;

public class RolInsertDTO {
    private UserRol nameRol;
    private String description;

    public UserRol getNameRol() {
        return nameRol;
    }

    public void setNameRol(UserRol nameRol) {
        this.nameRol = nameRol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

