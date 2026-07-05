package eva.pet.upc.evapet.controllers;


import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryInsertDTO;
import eva.pet.upc.evapet.dtos.medicalHistory.MedicalHistoryShowDTO;
import eva.pet.upc.evapet.models.MedicalHistory;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IEvaPetRepository;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceImplements.MedicalServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial-medico")
public class MedicalHistoryController {
    @Autowired
    MedicalServiceImplements mS;

    @Autowired
    IUsersRepository uR;

    @Autowired
    IEvaPetRepository eR;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR') ")
    @GetMapping("/historiales")
    public ResponseEntity<?> ListAll(Authentication authentication){
        ModelMapper m = new ModelMapper();

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        List<MedicalHistory> histories = mS.ListAll();
        if(histories.isEmpty()) return ResponseEntity.badRequest().body("No ningun historial medico");

        List<MedicalHistoryShowDTO> medicalShow = histories.stream().map(h-> m.map(h, MedicalHistoryShowDTO.class)).toList();
        return ResponseEntity.ok(medicalShow);
    }

    @PostMapping("/insertar/eva/{evaId}/hospital/{hospitalId}")
    public ResponseEntity<?> insert(@PathVariable Long evaId,
                                    @PathVariable Long hospitalId,
                                    @RequestBody MedicalHistoryInsertDTO dto,
                                    Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        try {
            MedicalHistory saved = mS.insert(dto, evaId, hospitalId);
            ModelMapper m = new ModelMapper();
            MedicalHistoryShowDTO response = m.map(saved, MedicalHistoryShowDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/historial-medico/actualizar/1
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody MedicalHistoryShowDTO dto,
                                    Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        try {
            ModelMapper m = new ModelMapper();
            MedicalHistory updated = mS.update(id, dto);

            MedicalHistoryShowDTO show = m.map(updated, MedicalHistoryShowDTO.class);

            return ResponseEntity.ok(show);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/historial-medico/eliminar/1
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Authentication authentication) {
        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");

        try {
            mS.delete(id);
            return ResponseEntity.ok("Historial médico desactivado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
