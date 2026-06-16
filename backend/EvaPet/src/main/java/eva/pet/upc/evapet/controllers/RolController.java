package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.rol.RolInsertDTO;
import eva.pet.upc.evapet.dtos.rol.RolShowDTO;
import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceImplements.RolServiceImplement;
import eva.pet.upc.evapet.serviceInterfaces.IRolService;
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

//@PreAuthorize("hasAuthority('ADMIN')")

@RestController
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private RolServiceImplement rS;

    @Autowired
    private IUsersRepository uR;

    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<?> listar(Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        ModelMapper m = new ModelMapper();
        List<RolShowDTO> listaRoles = rS.list().stream()
                .map(y -> m.map(y, RolShowDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaRoles);
    }

    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody RolInsertDTO dto, Authentication authentication){
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        if (dto.getNameRol() == null ) {
            return ResponseEntity.badRequest().body("Necesita un nombre válido");
        }

        Rol r = new Rol();
        r.setDescriptionRol(dto.getDescriptionRol());
        r.setNameRol(dto.getNameRol());

        Rol rol = rS.insert(r);

        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    // Solo doctor puede buscar un rol específico
    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        ModelMapper m = new ModelMapper();
        Optional<Rol> rolEncontrado = rS.listId(id);

        if (rolEncontrado.isPresent()) {
            RolInsertDTO dto = m.map(rolEncontrado.get(), RolInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }

    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@RequestBody RolInsertDTO dto, @PathVariable Long id, Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        Optional<Rol> existente = rS.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }

        ModelMapper m = new ModelMapper();
        Rol r = existente.get();

        m.map(dto, r);
        rS.update(r);

        return ResponseEntity.ok(m.map(r, RolInsertDTO.class));
    }

    @PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id, Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        Optional<Rol> rolEncontrado = rS.listId(id);

        if (rolEncontrado.isPresent()) {
            rS.delete(id);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
    }
}
