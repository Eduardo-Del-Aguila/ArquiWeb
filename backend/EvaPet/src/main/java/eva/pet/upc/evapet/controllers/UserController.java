package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.eva.EvaPetDTO;
import eva.pet.upc.evapet.dtos.eva.EvaPetInsertDTO;
import eva.pet.upc.evapet.dtos.user.UserDTO;
import eva.pet.upc.evapet.dtos.user.UsersInsertDTO;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.serviceImplements.RolServiceImplement;
import eva.pet.upc.evapet.serviceImplements.UsersServiceImplement;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    public UsersServiceImplement uS;
    @Autowired
    public RolServiceImplement rS;


    @GetMapping("/listar")
    public ResponseEntity<?> ListAll(){
        ModelMapper m = new ModelMapper();
        List<User> usuarios = uS.list();
        List<UserDTO> myUsers = usuarios.stream().map(p -> m.map(p, UserDTO.class)).toList();
        return ResponseEntity.ok(myUsers);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();

        Optional<User> existing = uS.listById(id);
        if (existing.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!existing.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        UserDTO dto = m.map(existing.get(), UserDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> Insert(@RequestBody UsersInsertDTO dto){
        ModelMapper m = new ModelMapper();
        Optional<Rol> rol = rS.listId(dto.getRolId());
        if (rol.isEmpty()) return ResponseEntity.badRequest().body("Rol no encontrado");

        //if(user.getName() == null | user.get() == null) return ResponseEntity.badRequest().body("Campos incompletos");
        //TODO: Hasher la contraseña
        User user = m.map(dto, User.class);

        user.setActive(true);
        user.setCreateAt(LocalDateTime.now());

        Rol myRol = rol.get();
        user.setIdRol(myRol);

        uS.insert(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody UsersInsertDTO dto, @PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<User> existing = uS.listById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        if (!existing.get().isActive()) return ResponseEntity.notFound().build();
        m.map(dto, existing);

        User user = existing.get();
        m.map(dto, user);

        uS.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> existing = uS.listById(id);
        if (existing.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!existing.get().isActive()) return ResponseEntity.badRequest().body("Usuario ya eliminado");

        existing.get().setActive(false);
        uS.update(existing.get());
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }




}
