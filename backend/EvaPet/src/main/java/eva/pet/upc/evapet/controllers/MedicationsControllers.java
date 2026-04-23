package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.MedicationsDTO;
import eva.pet.upc.evapet.dtos.MedicationsInsertDTO;
import eva.pet.upc.evapet.models.Medications;
import eva.pet.upc.evapet.serviceInterfaces.IMedicationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MedicationsControllers {
    @Autowired
    private IMedicationsService mS;
    @GetMapping
    public ResponseEntity<List<MedicationsDTO>> listar(){
        ModelMapper m=new ModelMapper();
        List<MedicationsDTO> listaMedications=mS.list().stream()
                .map(y->m.map(y,MedicationsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaMedications);
    }

    @PostMapping("/medications")
    public ResponseEntity<?> registrar(@RequestBody MedicationsInsertDTO dto){

        if (dto.getName() == null || dto.getName().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El nombre no puede ser vacío");
        }

        ModelMapper m=new ModelMapper();
        Medications med = m.map(dto, Medications.class);

        Medications nuevo = mS.insert(med);

        MedicationsInsertDTO responseDTO = m.map(nuevo, MedicationsInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Medications> med = mS.listId(id);

        if (med.isPresent()) {
            MedicationsInsertDTO dto = m.map(med.get(), MedicationsInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicamento no encontrado");
        }
    }

    @PutMapping("/medications")
    public ResponseEntity<String> actualizar(@RequestBody MedicationsInsertDTO dto) {

        Optional<Medications> existente = mS.listId(dto.getIdMedication());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicamento no encontrado");
        }

        Medications m = existente.get();

        m.setName(dto.getName());
        m.setDescription(dto.getDescription());
        m.setActive(dto.isActive());

        mS.update(m);

        return ResponseEntity.ok("Medicamento actualizado correctamente");
    }

    @DeleteMapping("/medications/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {

        Optional<Medications> med = mS.listId(id);

        if (med.isPresent()) {
            mS.delete(id);
            return ResponseEntity.ok("Medication eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medication no encontrado");
        }
    }




}
