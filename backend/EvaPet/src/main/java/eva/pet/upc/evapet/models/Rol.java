package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.UserRol;
import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(name= "nameRol", length = 20,nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRol nameRol;

    @Column(name= "descriptionRol", length = 200,nullable = false)
    private String descriptionRol;

    public Rol() {
    }
    public Rol(Long idRol, UserRol nameRol, String descriptionRol) {
        this.idRol = idRol;
        this.nameRol = nameRol;
        this.descriptionRol = descriptionRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public UserRol getNameRol() {
        return nameRol;
    }

    public void setNameRol(UserRol nameRol) {
        this.nameRol = nameRol;
    }

    public String getDescriptionRol() {
        return descriptionRol;
    }

    public void setDescriptionRol(String descriptionRol) {
        this.descriptionRol = descriptionRol;
    }
}

