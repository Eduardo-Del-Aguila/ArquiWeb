package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.hospital.HospitalInsertDTO;
import eva.pet.upc.evapet.dtos.hospital.ShowHospital;
import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.serviceImplements.HospitalServiceImplements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalServiceImplements hS;

    @GetMapping("/listar")
    public ResponseEntity<?> ListAll(){
        List<Hospital> hospitals = hS.listALL();
        if (hospitals.isEmpty()) return ResponseEntity.badRequest().body("No se contró ningun hospital");

        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> ListAll(@PathVariable Long id){
        Optional<Hospital> myHospital = hS.listById(id);
        if (myHospital.isEmpty()) return ResponseEntity.badRequest().body("No se contró ningun hospital con el ID: " + id);
        if (!myHospital.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");

        Hospital hosp = myHospital.get();

        return ResponseEntity.ok(hosp);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> Insert(@RequestBody HospitalInsertDTO dto){
        ModelMapper m = new ModelMapper();
        if (dto.getName().isEmpty()) return ResponseEntity.badRequest().body("Llenar todos los campos");
        Hospital hosp = m.map(dto, Hospital.class);
        hosp.setActive(true);
        hS.insert(hosp);


        ShowHospital hospital = m.map(hosp, ShowHospital.class);
        return ResponseEntity.ok(hospital);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> Update(@RequestBody HospitalInsertDTO dto, @PathVariable Long id){
        ModelMapper m = new ModelMapper();
        Optional<Hospital> myHosp = hS.listById(id);

        if (myHosp.isEmpty()) return ResponseEntity.badRequest().body("no exixte ningun hospital con el ID: " + id);
        if (!myHosp.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");

        Hospital hosp = m.map(myHosp.get(), Hospital.class);

        ShowHospital hospital = m.map(hosp, ShowHospital.class);
        return ResponseEntity.ok(hospital);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> Delete(@RequestBody HospitalInsertDTO dto, @PathVariable Long id){
        ModelMapper m = new ModelMapper();
        Optional<Hospital> myHosp = hS.listById(id);

        if (myHosp.isEmpty()) return ResponseEntity.badRequest().body("no exixte ningun hospital con el ID: " + id);
        if (!myHosp.get().isActive()) return ResponseEntity.badRequest().body("El hospital con el id: " + id + " está desactivado");


        Hospital hospi = m.map(myHosp.get(), Hospital.class);
        hospi.setActive(false);

        hS.deleteById(hospi);

        ShowHospital nuevito = m.map(hospi, ShowHospital.class);
        return ResponseEntity.ok(nuevito);
    }




}
