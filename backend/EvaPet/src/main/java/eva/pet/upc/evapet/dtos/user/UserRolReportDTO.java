package eva.pet.upc.evapet.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRolReportDTO {
    private String rol;
    private Long cantidad;
}