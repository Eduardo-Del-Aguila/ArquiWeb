package com.example.tablascarlos.serviceInterfaces;

import com.example.tablascarlos.dtos.hospital.HospitalDTO;
import com.example.tablascarlos.models.Hospital;

import java.util.List;

public interface HospitalService {

    String insertar(HospitalDTO dto);
    List<Hospital> listar();

    Hospital create(Hospital hospital);

    List<Hospital> getAll();

    Hospital getById(Integer id);

    Hospital update(Integer id, Hospital hospital);

    void delete(Integer id);

    List<Hospital> getByCity(String city);

    List<Hospital> getByName(String name);
}
