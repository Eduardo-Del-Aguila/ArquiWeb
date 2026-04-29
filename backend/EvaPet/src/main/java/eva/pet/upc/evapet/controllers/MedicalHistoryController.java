package eva.pet.upc.evapet.controllers;


import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryInsertDTO;
import eva.pet.upc.evapet.models.MedicalHistory;
import eva.pet.upc.evapet.serviceImplements.MedicalServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial-medico")
public class MedicalHistoryController {
    @Autowired
    MedicalServiceImplements mS;

    @GetMapping("historiales")
    public ResponseEntity<?> ListAll(){
        List<MedicalHistory> histories = mS.ListAll();
        if(histories.isEmpty()) return ResponseEntity.badRequest().body("No ningun historial medico");

        return ResponseEntity.ok(histories);
    }
    // /api/historial/paciente/3
// /api/historial/doctor/3
    @GetMapping("/{rol}/{id}")
    public ResponseEntity<?> listByRol(@PathVariable String rol, @PathVariable Long id) {
        Optional<MedicalHistory> result = switch (rol.toLowerCase()) {
            case "paciente" -> mS.ListByIdPatient(id);
            case "doctor"   -> mS.ListByIdDoctor(id);
            default -> null;
        };

        if (result == null) {
            return ResponseEntity.badRequest().body("Rol no válido. Usa: paciente, doctor");
        }
        if (result.isEmpty()) {
            return ResponseEntity.ok("No hay historiales para este " + rol);
        }

        ModelMapper m = new ModelMapper();
        List<MedicalHistoryInsertDTO> dtos = result.stream()
                .map(h -> m.map(h, MedicalHistoryInsertDTO.class))
                .toList();
        return ResponseEntity.ok(dtos);
    }




}
