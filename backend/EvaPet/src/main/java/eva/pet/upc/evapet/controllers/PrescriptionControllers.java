package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.prescription.PrescriptionDTO;
import eva.pet.upc.evapet.dtos.prescription.PrescriptionInsertDTO;
import eva.pet.upc.evapet.dtos.prescriptionmedications.RecipesPatientDTO;
import eva.pet.upc.evapet.models.Prescription;
import eva.pet.upc.evapet.serviceInterfaces.IPrescriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prescripcion")
public class PrescriptionControllers {
    @Autowired
    private IPrescriptionService pS;

    @GetMapping("/listar")
    public ResponseEntity<List<PrescriptionDTO>> listar(){
        ModelMapper m = new ModelMapper();

        List<PrescriptionDTO> lista = pS.list().stream()
                .map(y -> m.map(y, PrescriptionDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody PrescriptionInsertDTO dto){

        if(dto.getDiagnosis() == null || dto.getDiagnosis().isEmpty()){
            return ResponseEntity.badRequest().body("El diagnóstico no puede ser vacío");
        }

        ModelMapper m = new ModelMapper();
        Prescription p = m.map(dto, Prescription.class);

        Prescription nuevo = pS.insert(p);

        PrescriptionInsertDTO responseDTO = m.map(nuevo, PrescriptionInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id){

        ModelMapper m = new ModelMapper();
        Optional<Prescription> p = pS.listId(id);

        if(p.isPresent()){
            PrescriptionInsertDTO dto = m.map(p.get(), PrescriptionInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prescripcion no encontrado");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody PrescriptionInsertDTO dto){

        Optional<Prescription> existente = pS.listId(dto.getIdPrescription());

        if(existente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prescripcion no encontrado");
        }

        Prescription p = existente.get();

        p.setIdUserPatient(dto.getIdUserPatient());
        p.setIdEva(dto.getIdEva());
        p.setDiagnosis(dto.getDiagnosis());
        p.setDate(dto.getDate());

        pS.update(p);

        return ResponseEntity.ok("Prescripcion actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id){

        Optional<Prescription> p = pS.listId(id);

        if(p.isPresent()){
            pS.delete(id);
            return ResponseEntity.ok("Prescripcion eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prescription no encontrado");
        }
    }

    @GetMapping("/recetas-por-paciente")
    public ResponseEntity<?> recetasPorPaciente(){

        List<Object[]> lista = pS.RecipesPerPatient();

        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay registros");
        }

        List<RecipesPatientDTO> respuesta = new ArrayList<>();

        for(Object[] fila : lista){
            RecipesPatientDTO dto = new RecipesPatientDTO();

            dto.setIdUserPatient(((Number) fila[0]).intValue());
            dto.setTotalRecetas(((Number) fila[1]).intValue());

            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

}
