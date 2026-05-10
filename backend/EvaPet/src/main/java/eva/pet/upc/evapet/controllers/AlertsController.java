package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.alert.AlertsInsertDTO;
import eva.pet.upc.evapet.dtos.alert.ShowAlertsDTO;
import eva.pet.upc.evapet.models.Alerts;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceInterfaces.IAlertsService;
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

@RestController
@RequestMapping("/api/alerts")
public class AlertsController {

    @Autowired
    private IAlertsService aS;
    @Autowired
    private IUsersRepository uR;

    @Autowired
    private IEvaPetService eS;

    //@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<?> listar(Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        ModelMapper m = new ModelMapper();
        List<AlertsInsertDTO> listaAlertas = aS.list().stream()
                .map(y -> m.map(y, AlertsInsertDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaAlertas);
    }

    //@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody AlertsInsertDTO dto, Authentication authentication) {
        if (dto.getMessage() == null || dto.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La alerta necesita un mensaje válido");
        }

        // CORRECCIÓN 3: Validar quién está creando la alerta
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        // CORRECCIÓN 4: Validar que el paciente y la mascota (EvaPet) realmente existen en la BD
        Optional<User> patient = uR.findById(dto.getIdPatient());
        if (patient.isEmpty()) {
            return ResponseEntity.badRequest().body("El paciente asignado no existe");
        }

        Optional<EvaPet> pet = eS.listById(dto.getIdEva());
        if (pet.isEmpty()) {
            return ResponseEntity.badRequest().body("La mascota asignada no existe");
        }

        ModelMapper m = new ModelMapper();
        Alerts alerta = m.map(dto, Alerts.class);

        alerta.setIsRead(false);
        if (alerta.getCreatedAt() == null) {
            alerta.setCreatedAt(LocalDateTime.now());
        }

        // Opcional: Si tu modelo Alerts tiene un campo para registrar al creador, lo harías aquí.
        // alerta.setCreatedBy(currentUser.get().getId());

        Alerts alertaGuardada = aS.insert(alerta);
        AlertsInsertDTO responseDTO = m.map(alertaGuardada, AlertsInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    //@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Alerts> alerta = aS.listId(id);

        if (alerta.isPresent()) {
            AlertsInsertDTO dto = m.map(alerta.get(), AlertsInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta no encontrada");
        }
    }

    //TODO:Logica a elimnarse(quizas)
    //@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestBody AlertsInsertDTO dto, Authentication authentication) {
        ModelMapper m = new ModelMapper();
        // CORRECCIÓN 6: Ahora sí utilizamos el objeto Authentication para validar la sesión
        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        Optional<Alerts> existente = aS.listId(dto.getIdAlerts());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta no encontrada");
        }

        Alerts alertaActualizar = existente.get();
        // Opcional: Validar si el Doctor actual tiene permisos sobre esta alerta específica
        // if (!alertaActualizar.getCreatedBy().equals(currentUser.get().getId()) && noEsAdmin) { ... }

        alertaActualizar.setType(dto.getType());
        alertaActualizar.setMessage(dto.getMessage());
        alertaActualizar.setIsRead(true);
        aS.update(alertaActualizar);
        ShowAlertsDTO alert = m.map(alertaActualizar, ShowAlertsDTO.class);

        return ResponseEntity.ok(alert);

    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Optional<Alerts> alerta = aS.listId(id);

        if (alerta.isPresent()) {
            aS.delete(id);
            return ResponseEntity.ok("Alerta eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alerta no encontrada");
        }
    }

    //@PreAuthorize("hasAuthority('DOCTOR') or hasAuthority('ADMIN')")
    @GetMapping("/no-leidas/{idPaciente}")
    public ResponseEntity<?> listarNoLeidasPorPaciente(@PathVariable Long idPaciente, Authentication authentication) {

        String mail = authentication.getName();
        Optional<User> currentUser = uR.findUserByMail(mail);
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autorizado");
        }

        Optional<User> paciente = uR.findById(idPaciente);
        if (paciente.isEmpty()) {
            return ResponseEntity.badRequest().body("El paciente no existe");
        }

        // 3. LLAMAMOS AL SERVICIO (El servicio se encarga de ejecutar el Query)
        List<Alerts> alertasNoLeidas = aS.listarNoLeidasPorPaciente(idPaciente);

        // 4. Mapeamos a DTO (Usamos ShowAlertsDTO para devolver la info completa con ID)
        ModelMapper m = new ModelMapper();
        List<ShowAlertsDTO> response = alertasNoLeidas.stream()
                .map(alerta -> m.map(alerta, ShowAlertsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}