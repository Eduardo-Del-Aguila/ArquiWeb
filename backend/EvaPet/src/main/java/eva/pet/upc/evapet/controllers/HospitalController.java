package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.hospital.HospitalInsertDTO;
import eva.pet.upc.evapet.dtos.hospital.HospitalShowDTO;
import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceImplements.HospitalServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalServiceImplements hS;

    @Autowired
    private IUsersRepository uR;

    @GetMapping("/listar")
    public ResponseEntity<?> ListAll(Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        List<Hospital> hospitals = hS.listALL();
        if (hospitals.isEmpty()) return ResponseEntity.badRequest().body("No se contró ningun hospital");
        ModelMapper m = new ModelMapper();
        List<HospitalShowDTO> show = hospitals.stream().map( h -> m.map(h ,HospitalShowDTO.class)).toList();


        return ResponseEntity.ok(show);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> ListAll(@PathVariable Long id, Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        Optional<Hospital> myHospital = hS.listById(id);
        if (myHospital.isEmpty()) return ResponseEntity.badRequest().body("No se contró ningun hospital con el ID: " + id);
        if (!myHospital.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");

        ModelMapper m = new ModelMapper();
        Hospital hosp = myHospital.get();

        HospitalShowDTO show = m.map(hosp,HospitalShowDTO.class);

        return ResponseEntity.ok(show);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> Insert(@RequestBody HospitalInsertDTO dto, Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        ModelMapper m = new ModelMapper();
        if (dto.getName().isEmpty()) return ResponseEntity.badRequest().body("Llenar todos los campos");
        Hospital hosp = m.map(dto, Hospital.class);
        hosp.setActive(true);
        hS.insert(hosp);


        HospitalShowDTO hospital = m.map(hosp, HospitalShowDTO.class);
        return ResponseEntity.ok(hospital);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> Update(@RequestBody HospitalInsertDTO dto, @PathVariable Long id, Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        ModelMapper m = new ModelMapper();
        Optional<Hospital> myHosp = hS.listById(id);

        if (myHosp.isEmpty()) return ResponseEntity.badRequest().body("no exixte ningun hospital con el ID: " + id);
        if (!myHosp.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");

        Hospital hosp = m.map(myHosp.get(), Hospital.class);

        HospitalShowDTO hospital = m.map(hosp, HospitalShowDTO.class);

        return ResponseEntity.ok(hospital);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> Delete(@PathVariable Long id, Authentication authentication){

        String mail = authentication.getName();
        Optional<User> user = uR.findUserByMail(mail);
        if (user.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
        if (!user.get().isActive()) return ResponseEntity.badRequest().body("Usuario inactivo");


        ModelMapper m = new ModelMapper();
        Optional<Hospital> myHosp = hS.listById(id);

        if (myHosp.isEmpty()) return ResponseEntity.badRequest().body("no exixte ningun hospital con el ID: " + id);
        if (!myHosp.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");


        Hospital hospi = m.map(myHosp.get(), Hospital.class);
        hospi.setActive(false);

        hS.deleteById(hospi);

        HospitalShowDTO nuevito = m.map(hospi, HospitalShowDTO.class);
        return ResponseEntity.ok(nuevito);
    }




}
