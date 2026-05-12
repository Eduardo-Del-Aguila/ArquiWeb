package eva.pet.upc.evapet.controllers;


import eva.pet.upc.evapet.dtos.eva.EvaPetDTO;
import eva.pet.upc.evapet.dtos.eva.EvaPetInsertDTO;
import eva.pet.upc.evapet.dtos.eva.EvaPetShowDTO;
import eva.pet.upc.evapet.enums.StatusPet;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceImplements.UsersServiceImplement;
import eva.pet.upc.evapet.serviceInterfaces.IEvaPetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/pet")
public class EvaPetController {
    @Autowired
    private IEvaPetService eS;

    @Autowired
    private IUsersRepository uR;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<?> List(Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        ModelMapper m = new ModelMapper();
        List<EvaPet> pets = eS.listAll();
        if (pets.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay macotas registradas");

        List<EvaPetDTO> myPets = pets.stream().map(p -> m.map(p,EvaPetDTO.class)).toList();
        return ResponseEntity.ok(myPets);
    }

    //CRUD COMPLETO
    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    @GetMapping("listar/{id}")
    public ResponseEntity<?> listByID(@PathVariable Long id, Authentication authentication){
        ModelMapper m = new ModelMapper();

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        Optional<EvaPet> myEva = eS.listById(id);
        if(myEva.isEmpty()) return  ResponseEntity.badRequest().body("No exisite una mascota con el id: " + id);

        EvaPetDTO evita = m.map(myEva.get(), EvaPetDTO.class);

        return ResponseEntity.ok(evita);
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    @PostMapping("/insertar")
    public ResponseEntity<?> insert(@RequestBody EvaPetInsertDTO dto, Authentication authentication) {
        ModelMapper m = new ModelMapper();
        if (dto.getName() == null || dto.getDescription() == null)
            return ResponseEntity.badRequest().body("Campos incompletos");

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        List<String> names = eS.listByName();
        if (names.contains(dto.getName())) return ResponseEntity.badRequest().body("Nombre ocupado");

        EvaPet eva = m.map(dto, EvaPet.class);
        eva.setPatient(user.get());
        eva.setActive(true);
        eva.setCreateAt(LocalDateTime.now());
        eva.setLastInteraction(LocalDateTime.now());
        eva.setLevel(0);
        eva.setExperiencie(0);
        eva.setStatus(dto.getStatus());

        eS.insert(eva);
        EvaPetShowDTO show = m.map(eva, EvaPetShowDTO.class);

        return ResponseEntity.ok(show);
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody EvaPetInsertDTO dto,
                                    @PathVariable Long id,
                                    Authentication authentication) {
        ModelMapper m = new ModelMapper();

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        Optional<EvaPet> optional = eS.listById(id);
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Mascota no encontrada");

        EvaPet myEva = optional.get();
        if (!myEva.getPatient().getId().equals(user.get().getId()))
            return ResponseEntity.badRequest().body("No tienes permiso para modificar esta mascota");

        if (!myEva.getName().equals(dto.getName())) {
            List<String> names = eS.listByName();
            if (names.contains(dto.getName()))
                return ResponseEntity.badRequest().body("Nombre ocupado");
        }

        m.map(dto, myEva);
        eS.update(myEva);
        return ResponseEntity.ok(m.map(myEva, EvaPetInsertDTO.class));
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");

        Optional<EvaPet> optional = eS.listById(id);
        if (optional.isEmpty()) return ResponseEntity.badRequest().body("Mascota no encontrada");

        EvaPet myEva = optional.get();
        if (!myEva.getPatient().getId().equals(user.get().getId()))
            return ResponseEntity.badRequest().body("No tienes permiso para eliminar esta mascota");

        myEva.setActive(false);
        Optional<EvaPet> evi = eS.listById(myEva.getId());
        if (evi.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe eva con ese Id: " + myEva.getId());

        eS.delete(evi.get());
        return ResponseEntity.ok("Mascota " + myEva.getName() + " eliminada correctamente");
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('ADMIN')")
    @GetMapping("/mis-mascotas/por-nivel")
    public ResponseEntity<?> listMyPetsByLevel(Authentication authentication) {
        ModelMapper m = new ModelMapper();

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        List<EvaPet> pets = eS.findTopByPatientOrderedByLevel(user.get().getId());
        if (pets.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tienes mascotas registradas");

        List<EvaPetDTO> myPets = pets.stream().map(p -> m.map(p, EvaPetDTO.class)).toList();
        return ResponseEntity.ok(myPets);
    }

}
