package eva.pet.upc.evapet.dtos.hospital;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "name", "direction", "phone", "city" })
public class HospitalShowDTO {
    private String name;
    private String direction;
    private String phone;
    private String city;
}
