package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.rol.RolInsertDTO;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.serviceInterfaces.IRolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rol")
public class RolController {
    @Autowired
    private IRolService rS;
    @GetMapping
    public ResponseEntity<?> listar() {
        ModelMapper m= new ModelMapper();
        List<RolInsertDTO> listaRoles=rS.list().stream()
                .map(y->m.map(y, RolInsertDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaRoles);
    }
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody RolInsertDTO dto){

        if (dto.getNameRol() == null ) {
            return ResponseEntity.badRequest()
                    .body("Necesita un nombre valido");
        }
        ModelMapper m=new ModelMapper();
        Rol r=m.map(dto, Rol.class);
        Rol rol= rS.insert(r);
        RolInsertDTO responseDTO=m.map(rol, RolInsertDTO.class);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Rol> autor = rS.listId(id);

        if (autor.isPresent()) {
            RolInsertDTO dto = m.map(autor.get(), RolInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }
    }

    @PutMapping("/actualiza")
    public ResponseEntity<String> actualizar(@RequestBody RolInsertDTO dto) {

        Optional<Rol> existente = rS.listId(dto.getIdRol());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        Rol r = existente.get();

        r.setNameRol(dto.getNameRol());
        r.setDescriptionRol(dto.getDescription());


        rS.update(r);

        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Optional<Rol> autor = rS.listId(id);

        if (autor.isPresent()) {
            rS.delete(id);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }
    }
}
