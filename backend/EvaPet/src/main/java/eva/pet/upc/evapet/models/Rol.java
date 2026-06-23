package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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


}

