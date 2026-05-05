package eva.pet.upc.evapet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "direction", nullable = false)
    private String direction;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

}
