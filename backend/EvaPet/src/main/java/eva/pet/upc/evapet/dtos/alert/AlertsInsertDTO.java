package eva.pet.upc.evapet.dtos.alert;

import eva.pet.upc.evapet.enums.AlertType;

public class AlertsInsertDTO {
    private Long idAlerts;
    private AlertType type;
    private String message;
    private Boolean isRead;
    private int idPatient;
    private int idEva;

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Long getIdAlerts() {
        return idAlerts;
    }

    public void setIdAlerts(Long idAlerts) {
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
