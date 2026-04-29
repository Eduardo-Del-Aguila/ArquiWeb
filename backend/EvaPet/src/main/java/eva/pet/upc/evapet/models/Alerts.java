package eva.pet.upc.evapet.models;

import eva.pet.upc.evapet.enums.AlertType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Alerts")
public class Alerts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlerts;

    @Column(name = "type",length = 200, nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType type;

    @Column(name = "message",length = 200, nullable = false)
    private String message;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //NO ESTAN AVANZADAS SE PONE ASI PARA PODER AVANZAR
    @Column(name = "id_patient", nullable = false)
    private int idPatient;
    @Column(name = "id_eva", nullable = false)
    private int idEva;
    // NO ESTAN AVANZADAS AUN

    public Alerts() {
    }
    public Alerts(int idAlerts, AlertType type, String message, Boolean isRead, LocalDateTime scheduledAt, LocalDateTime createdAt, int idPatient, int idEva) {
        this.idAlerts = idAlerts;
        this.type = type;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.idPatient = idPatient;
        this.idEva = idEva;
    }

    public int getIdAlerts() {
        return idAlerts;
    }

    public void setIdAlerts(int idAlerts) {
        this.idAlerts = idAlerts;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getIdEva() {
        return idEva;
    }

    public void setIdEva(int idEva) {
        this.idEva = idEva;
    }
}
