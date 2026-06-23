package com.example.tablascarlos.serviceImplements;


import com.example.tablascarlos.dtos.hospital.HospitalDTO;
import com.example.tablascarlos.models.Hospital;
import com.example.tablascarlos.repositories.HospitalRepository;
import com.example.tablascarlos.serviceInterfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImplement implements HospitalService {

    @Autowired
    private HospitalRepository repository;

    @Override
    public String insertar(HospitalDTO dto) {
        return "";
    }

    @Override
    public List<Hospital> listar() {
        return List.of();
    }

    @Override
    public Hospital create(Hospital hospital) {
        return repository.save(hospital);
    }

    @Override
    public List<Hospital> getAll() {
        return repository.findAll();
    }

    @Override
    public Hospital getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Hospital update(Integer id, Hospital hospital) {
        Hospital existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(hospital.getName());
            existing.setDirection(hospital.getDirection());
            existing.setPhone(hospital.getPhone());
            existing.setCity(hospital.getCity());
            return repository.save(existing);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Hospital> getByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<Hospital> getByName(String name) {
        return repository.findByNameContaining(name);
    }
}

