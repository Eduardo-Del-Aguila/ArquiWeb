package eva.pet.upc.evapet.dtos.eva;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaPetReportDTO {
    private String petName;
    private int level;
    private String ownerName;
}