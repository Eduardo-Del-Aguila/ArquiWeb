package eva.pet.upc.evapet.dtos.securities;

import java.io.Serializable;

public class JwtResponseDTO implements Serializable {

    private final String jwttoken;

    public String getJwttoken() {
        return jwttoken;
    }

    public JwtResponseDTO(String jwttoken) {
        super();
        this.jwttoken = jwttoken;
    }

}