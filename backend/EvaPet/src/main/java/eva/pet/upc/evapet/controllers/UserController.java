package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.eva.EvaPetDTO;
import eva.pet.upc.evapet.dtos.eva.EvaPetInsertDTO;
import eva.pet.upc.evapet.dtos.user.*;
import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IRolRepository;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceImplements.CloudinaryService;
import eva.pet.upc.evapet.serviceImplements.RolServiceImplement;
import eva.pet.upc.evapet.serviceImplements.UsersServiceImplement;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

//@PreAuthorize("hasAuthority('ADMIN')"  )
@RestController
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    public UsersServiceImplement uS;
    @Autowired
    public IUsersRepository uR;
    @Autowired
    public RolServiceImplement rS;
    @Autowired
    public IRolRepository rR;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/listar")
    public ResponseEntity<?> ListAll(){
        ModelMapper m = new ModelMapper();
        List<User> usuarios = uS.list();
        List<UserShowDTO> myUsers = usuarios.stream().map(p -> m.map(p, UserShowDTO.class)).toList();
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


//    @PreAuthorize("hasAuthority('FAMILY') || hasAuthority('ADMIN')")
    @PostMapping(value = "/insertar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insert(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("rolId") Long rolId,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen, HttpMethod httpMethod) {

        Optional<Rol> rol = rS.listId(rolId);
        //el más comun not_Found
        if (rol.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");

        Optional<User> userD = uS.listByIdDeleted(mail);

        if (userD.isPresent()){
            return ResponseEntity.ok("usuario " +  mail + " activado correctamente");
        }

        Optional<User> myUser = uS.findUserByMail(mail);
        if (myUser.isPresent()){
            //CONFLICT su mismo nombre lo dice hay un conflicto
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya registrado");
        }

        String imageUrl = "https://res.cloudinary.com/demo/image/upload/sample.jpg";
        if (imagen != null && !imagen.isEmpty()) {
            try {
                imageUrl = cloudinaryService.upload(imagen);
            } catch (IOException e) {
                //INTERNAL_SERVER_ERROR para errores del servidor
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
            }
        }



        ModelMapper m = new ModelMapper();

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setMail(mail);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setImage_url(imageUrl);
        user.setRol(rol.get());
        user.setActive(true);
        user.setCreateAt(LocalDateTime.now());

        uS.insert(user);

        UserShowDTO show = m.map(user, UserShowDTO.class);


        return ResponseEntity.ok(show);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value ="/actualizar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("nameRol") UserRol nameRol,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("mail") String mail,
            @PathVariable Long id
    ) {

        ModelMapper m = new ModelMapper();

        Optional<User> existing = uS.listById(id);

        if (existing.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");

        User user = existing.get();

        if (!user.isActive())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario desactivado");

        Optional<Rol> rolsito = rS.ListByName(nameRol);

        if (rolsito.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("rol incorrecto");

        String imageUrl = "https://res.cloudinary.com/demo/image/upload/sample.jpg";
        if (imagen != null && !imagen.isEmpty()) {
            try {
                imageUrl = cloudinaryService.upload(imagen);
            } catch (IOException e) {
                //INTERNAL_SERVER_ERROR para errores del servidor
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
            }
        }


        user.setName(name);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setMail(mail);
        user.setImage_url(imageUrl);
        user.setRol(rolsito.get());
        uS.update(user);

        UserDTO toShow = m.map(user, UserDTO.class);

        return ResponseEntity.ok(toShow);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> existing = uS.listById(id);
        if (existing.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!existing.get().isActive()) return ResponseEntity.badRequest().body("Usuario ya eliminado");

        existing.get().setActive(false);
        uS.update(existing.get());
        return ResponseEntity.ok("Usuario " + id + " eliminado correctamente");
    }

    // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/reporte/roles")
    public ResponseEntity<?> reporteRoles() {
        //String mail = authentication.getName();
        //Optional<User> user = uR.findUserByMail(mail);
        //if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        //if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        List<UserRolReportDTO> reporte = uS.getRolReport();
        if (reporte.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");

        return ResponseEntity.ok(reporte);
    }

}
