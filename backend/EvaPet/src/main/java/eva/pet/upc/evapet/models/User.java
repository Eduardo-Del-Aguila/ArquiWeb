package eva.pet.upc.evapet.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "lastName",nullable = false)
    private String lastName;
    @Column(name = "mail",nullable = false)
    private String mail;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "image_url",nullable = false)
    private String image_url;
    @Column(name = "phoneNumber",nullable = false)
    private String phoneNumber;
    @Column(name = "isActive")
    private boolean isActive;
    @Column(name = "createAt")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

}
