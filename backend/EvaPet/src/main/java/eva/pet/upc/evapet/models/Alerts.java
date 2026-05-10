package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.AlertType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "alerts")
public class Alerts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerts;

    @Column(name = "type",length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType type;

    @Column(name = "message",length = 200, nullable = false)
    private String message;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "id_eva")
    private EvaPet evaPet;

}
