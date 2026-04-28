package eva.pet.upc.evapet.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private String image_url;
    private String phoneNumber;
    private boolean isActive;
    private LocalDateTime createAt;



}
