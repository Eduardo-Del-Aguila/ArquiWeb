package eva.pet.upc.evapet.dtos.alert;

import eva.pet.upc.evapet.enums.AlertType;
import lombok.Data;

@Data
public class AlertsInsertDTO {
    private AlertType type;
    private String message;
    //private Boolean isRead;
    private Long idPatient;
    private Long idEva;
}
