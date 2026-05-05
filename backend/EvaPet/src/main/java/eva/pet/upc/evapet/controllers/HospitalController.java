package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.hospital.HospitalInsertDTO;
import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.serviceImplements.HospitalServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalServiceImplements hS;

    @GetMapping("/listar")
    public ResponseEntity<?> ListAll(){
        List<Hospital> hospitals = hS.listALL();
        if (hospitals.isEmpty()) return ResponseEntity.badRequest().body("No se contró ningun hospital");

        return ResponseEntity.ok(hospitals);
    }
    @PostMapping("/insertar")
    public ResponseEntity<?> Insert(@RequestBody HospitalInsertDTO dto){
        ModelMapper m = new ModelMapper();
        if (dto.getName().isEmpty()) return ResponseEntity.badRequest().body("Llenar todos los campos");
        Hospital hospi = m.map(dto, Hospital.class);
        hS.update(hospi);

        return ResponseEntity.ok(hospi);
    }
}
