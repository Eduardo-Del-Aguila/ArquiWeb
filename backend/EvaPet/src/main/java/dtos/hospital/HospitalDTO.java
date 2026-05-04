package dtos.hospital;

import lombok.Data;

@Data
public class HospitalDTO {

    private String name;
    private String address;
    private String district;
    private String province;
    private String department;
    private String phone;
    private String email;
    private String state;
}