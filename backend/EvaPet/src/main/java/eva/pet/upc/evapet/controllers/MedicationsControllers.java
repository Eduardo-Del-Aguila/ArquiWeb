package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.medications.MedicationsDTO;
import eva.pet.upc.evapet.dtos.medications.MedicationsInsertDTO;
import eva.pet.upc.evapet.models.Medications;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceInterfaces.IMedicationsService;
import eva.pet.upc.evapet.serviceInterfaces.IUsersService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/medicamentos")
public class MedicationsControllers {
    @Autowired
    private IMedicationsService mS;

    @Autowired
    private IUsersRepository uR;

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Authentication a){

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        ModelMapper m=new ModelMapper();
        List<MedicationsDTO> listaMedications=mS.list().stream()
                .map(y->m.map(y,MedicationsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaMedications);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody MedicationsInsertDTO dto, Authentication a){

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El nombre no puede ser vacío");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("La descripción no puede ser vacía");
        }

        boolean existe = mS.existsByName(dto.getName());
        if (existe) {
            return ResponseEntity.badRequest()
                    .body("Ya existe un medicamento con ese nombre");
        }

        ModelMapper m = new ModelMapper();
        Medications med = m.map(dto, Medications.class);

        Medications nuevo = mS.insert(med);

        MedicationsInsertDTO responseDTO = m.map(nuevo, MedicationsInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/listar/{id}")
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

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody MedicationsInsertDTO dto, Authentication a) {

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        Optional<Medications> existente = mS.listId(dto.getIdMedication());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicamento no encontrado");
        }

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El nombre no puede ser vacío");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("La descripción no puede ser vacía");
        }

        boolean existe = mS.existsByName(dto.getName());
        Medications actual = existente.get();

        if (existe && !actual.getName().equalsIgnoreCase(dto.getName())) {
            return ResponseEntity.badRequest()
                    .body("Ya existe otro medicamento con ese nombre");
        }

        actual.setName(dto.getName().trim());
        actual.setDescription(dto.getDescription().trim());
        actual.setActive(dto.isActive());

        mS.update(actual);

        return ResponseEntity.ok("Medicamento actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id, Authentication a) {

        String mail = a.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        Optional<Medications> med = mS.listId(id);

        if (med.isPresent()) {
            mS.delete(id);
            return ResponseEntity.ok("Medication eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medication no encontrado");
        }
    }

    @GetMapping("/medicamentos-activos")
    public ResponseEntity<?> listarActivos(){

        ModelMapper m = new ModelMapper();

        List<MedicationsDTO> lista = mS.listarMedicamentosActivos()
                .stream()
                .map(x -> m.map(x, MedicationsDTO.class))
                .collect(Collectors.toList());

        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay medicamentos activos");
        }

        return ResponseEntity.ok(lista);
    }




}
