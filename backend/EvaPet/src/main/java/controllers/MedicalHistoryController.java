package controllers;

import dtos.medicalHistory.MedicalHistoryDTO;
import models.MedicalHistory;
import serviceInterfaces.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalhistory")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService service;

    @PostMapping
    public String insertar(@RequestBody MedicalHistoryDTO dto) {
        return service.insertar(dto);
    }

    @GetMapping
    public List<MedicalHistory> listar() {
        return service.listar();
    }
}