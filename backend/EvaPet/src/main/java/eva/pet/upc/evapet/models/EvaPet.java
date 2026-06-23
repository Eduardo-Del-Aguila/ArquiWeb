package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.StatusPet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pet")
public class EvaPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "name", length = 25, nullable = false)
    private String name;
    @Column(name = "description", length = 200, nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPet status;
    @Column(name = "level", nullable = false)
    private int level;
    @Column(name = "experiencie", nullable = false)
    private int experiencie;
    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;
    @Column(name = "lastInteraction", nullable = false)
    private LocalDateTime lastInteraction;
    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private User patient;

}
