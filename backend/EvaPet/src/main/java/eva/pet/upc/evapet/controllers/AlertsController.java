package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.alert.AlertsInsertDTO;
import eva.pet.upc.evapet.models.Alerts;
import eva.pet.upc.evapet.serviceInterfaces.IAlertsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // ✅ Importación de seguridad
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

    // ✅ ADMIN y VETERINARIO pueden ver todas las alertas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VETERINARIO')")
    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        ModelMapper m = new ModelMapper();
        List<AlertsInsertDTO> listaAlertas = aS.list().stream()
                .map(y -> m.map(y, AlertsInsertDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaAlertas);
    }

    // ✅ ADMIN y VETERINARIO (o el sistema) pueden crear alertas
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VETERINARIO')")
    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody AlertsInsertDTO dto) {
        if (dto.getMessage() == null || dto.getMessage().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("La alerta necesita un mensaje válido");
        }

        ModelMapper m = new ModelMapper();
        Alerts alerta = m.map(dto, Alerts.class);

        alerta.setIsRead(false);
        if (alerta.getCreatedAt() == null) {
            alerta.setCreatedAt(LocalDateTime.now());
        }

        Alerts alertaGuardada = aS.insert(alerta);
        AlertsInsertDTO responseDTO = m.map(alertaGuardada, AlertsInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // ✅ ADMIN y VETERINARIO pueden buscar una alerta específica
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VETERINARIO')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        Optional<Alerts> alerta = aS.listId(id);

        if (alerta.isPresent()) {
            AlertsInsertDTO dto = m.map(alerta.get(), AlertsInsertDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Alerta no encontrada");
        }
    }

    // ✅ ADMIN y VETERINARIO pueden actualizar (ej. para marcar como leída)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'VETERINARIO')")
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody AlertsInsertDTO dto) {
        Optional<Alerts> existente = aS.listId(dto.getIdAlerts());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Alerta no encontrada");
        }

        Alerts alertaActualizar = existente.get();

        alertaActualizar.setType(dto.getType());
        alertaActualizar.setMessage(dto.getMessage());
        alertaActualizar.setIsRead(true); // Asumiendo que al actualizar se marca como leída
        alertaActualizar.setIdPatient(dto.getIdPatient());
        alertaActualizar.setIdEva(dto.getIdEva());

        aS.update(alertaActualizar);

        return ResponseEntity.ok("Alerta actualizada correctamente");
    }

    // ⛔ Solo ADMIN puede eliminar alertas del historial
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        Optional<Alerts> alerta = aS.listId(id);

        if (alerta.isPresent()) {
            aS.delete(id);
            return ResponseEntity.ok("Alerta eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Alerta no encontrada");
        }
    }
}