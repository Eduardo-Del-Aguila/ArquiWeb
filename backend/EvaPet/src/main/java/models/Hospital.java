package com.example.tablascarlos.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hospital")
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hospital")
    private Integer idHospital;

    private String name;
    private String direction;
    private String phone;
    private String city;

    public void setAddress(String address) {
    }
}