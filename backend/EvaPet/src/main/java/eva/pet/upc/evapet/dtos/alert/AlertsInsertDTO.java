package eva.pet.upc.evapet.dtos.alert;

import eva.pet.upc.evapet.enums.AlertType;
import lombok.Data;

@Data
public class AlertsInsertDTO {
    private Long idAlerts;
    private AlertType type;
    private String message;
    //private Boolean isRead;
    private int idPatient;
    private int idEva;

}
