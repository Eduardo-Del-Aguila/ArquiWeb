package eva.pet.upc.evapet.dtos.alert;

import eva.pet.upc.evapet.enums.AlertType;
import lombok.Data;

@Data
public class ShowAlertsDTO {
    private AlertType type;
    private String message;
    private Boolean isRead;
    private String patientName;
}
