package eva.pet.upc.evapet.dtos.alert;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eva.pet.upc.evapet.enums.AlertType;
import lombok.Data;

@Data
@JsonPropertyOrder({ "type", "message", "isRead", "patientName" })
public class ShowAlertsDTO {
    private AlertType type;
    private String message;
    private Boolean isRead;
    private String patientName;
}
