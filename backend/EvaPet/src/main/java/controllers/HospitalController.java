package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private com.example.tablascarlos.serviceInterfaces.HospitalService service;

    @PostMapping
    public String insertar(@RequestBody com.example.tablascarlos.dtos.hospital.HospitalDTO dto) {
        return service.insertar(dto);
    }

    @GetMapping
    public List<com.example.tablascarlos.models.Hospital> listar() {
        return service.listar();
    }
}