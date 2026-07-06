package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.prescription.PrescriptionDTO;
import eva.pet.upc.evapet.dtos.prescription.PrescriptionInsertDTO;
import eva.pet.upc.evapet.dtos.prescription.PrescriptionShowDTO;
import eva.pet.upc.evapet.dtos.prescriptionmedications.RecipesPatientDTO;
import eva.pet.upc.evapet.models.Prescription;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceInterfaces.IPrescriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/prescripcion")
public class PrescriptionControllers {

    @Autowired
    private IUsersRepository uR;

    @Autowired
    private IPrescriptionService pS;

    @GetMapping("/listar")
    public ResponseEntity<List<PrescriptionShowDTO>> listar(){
        ModelMapper m = new ModelMapper();

        List<PrescriptionShowDTO> lista = pS.list().stream()
                .map(x -> new ModelMapper().map(x, PrescriptionShowDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody PrescriptionInsertDTO dto, Authentication a){

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        if (dto.getIdUserPatient() <= 0) {
            return ResponseEntity.badRequest()
                    .body("El ID del paciente debe ser mayor a 0");
        }

        if (dto.getIdEva() <= 0) {
            return ResponseEntity.badRequest()
                    .body("El ID de Eva debe ser mayor a 0");
        }

        if (dto.getDate() == null) {
            return ResponseEntity.badRequest()
                    .body("La fecha no puede ser nula");
        }

        if (dto.getDate().isAfter(java.time.LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body("La fecha no puede ser futura");
        }

        ModelMapper m = new ModelMapper();
        Prescription p = m.map(dto, Prescription.class);

        Prescription nuevo = pS.insert(p);

        PrescriptionDTO response = m.map(nuevo, PrescriptionDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@RequestBody PrescriptionInsertDTO dto,@PathVariable int id, Authentication a){

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        Optional<Prescription> existente = pS.listId(id);

        if(existente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Prescripcion no encontrado");
        }

        if (dto.getIdUserPatient() <= 0) {
            return ResponseEntity.badRequest().body("ID paciente inválido");
        }

        if (dto.getIdEva() <= 0) {
            return ResponseEntity.badRequest().body("ID eva inválido");
        }

        if (dto.getDiagnosis() == null || dto.getDiagnosis().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Diagnóstico vacío");
        }

        if (dto.getDate() == null) {
            return ResponseEntity.badRequest().body("Fecha nula");
        }

        if (dto.getDate().isAfter(java.time.LocalDate.now())) {
            return ResponseEntity.badRequest().body("Fecha futura no permitida");
        }

        Prescription p = existente.get();

        p.setIdUserPatient(dto.getIdUserPatient());
        p.setIdEva(dto.getIdEva());
        p.setDiagnosis(dto.getDiagnosis().trim());
        p.setDate(dto.getDate());

        pS.update(p);

        return ResponseEntity.ok("Prescripcion actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id, Authentication a){

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

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
