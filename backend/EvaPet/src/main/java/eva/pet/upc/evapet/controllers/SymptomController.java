package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.SymptomInsertDTO;
import eva.pet.upc.evapet.models.Symptom;
import eva.pet.upc.evapet.serviceInterfaces.ISymptomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sintomas")
public class SymptomController {
    @Autowired
    private ISymptomService sS;

    @GetMapping("/listar")
    public ResponseEntity<List<SymptomInsertDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<SymptomInsertDTO> listaSymptoms = sS.List().stream()
                .map(y -> m.map(y, SymptomInsertDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaSymptoms);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody SymptomInsertDTO dto) {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El nombre del síntoma no puede ser vacío");
        }

        if (dto.getSeverity() == null) {
            return ResponseEntity.badRequest()
                    .body("La severidad no puede ser nula");
        }

        ModelMapper m = new ModelMapper();
        Symptom s = m.map(dto, Symptom.class);
        Symptom symptom = sS.insert(s);
        SymptomInsertDTO responseDTO = m.map(symptom, SymptomInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<String> actualizar(@RequestBody SymptomInsertDTO dto, @PathVariable Long id) {

        Optional<Symptom> existente = null;

        try {
            existente = sS.ListById(id);
            if (existente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Síntoma no encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (dto.getName() == null || dto.getName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El nombre del síntoma no puede ser vacío");
        }

        if (dto.getSeverity() == null) {
            return ResponseEntity.badRequest()
                    .body("La severidad no puede ser nula");
        }

        Symptom s = existente.get();

        s.setName(dto.getName());
        s.setSeverity(dto.getSeverity());

        sS.update(s);

        return ResponseEntity.ok("Síntoma actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable long id) {
        Optional<Symptom> symptom = sS.ListById(id);

        if (symptom.isPresent()) {
            sS.delete(id);
            return ResponseEntity.ok("Síntoma eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Síntoma no encontrado");
        }
    }
}
