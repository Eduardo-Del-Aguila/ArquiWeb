package eva.pet.upc.evapet.controllers;


import eva.pet.upc.evapet.dtos.eva.EvaPetDTO;
import eva.pet.upc.evapet.dtos.eva.EvaPetInsertDTO;
import eva.pet.upc.evapet.models.EvaPet;
import eva.pet.upc.evapet.serviceInterfaces.IEvaPetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/evapet")
public class EvaPetController {
    @Autowired
    private IEvaPetService eS;

    @GetMapping("/mascotas")
    public ResponseEntity<List<EvaPetDTO>> List(){
        ModelMapper m = new ModelMapper();
        List<EvaPet> pets = eS.getAll().stream().filter(EvaPet::isActive).toList();

        List<EvaPetDTO> myPets = pets.stream().map(p -> m.map(p,EvaPetDTO.class)).toList();


        return ResponseEntity.ok(myPets);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> Insert(@RequestBody EvaPetInsertDTO evaI){
        ModelMapper m = new ModelMapper();
        if(evaI.getName() == null | evaI.getDescription() == null) return ResponseEntity.badRequest().body("Campos incompletos");
        List<String> names = eS.getAllNames();
        if(names.contains(evaI.getName())) return ResponseEntity.badRequest().body("Nombre ocupado");

        EvaPet eva = m.map(evaI, EvaPet.class);
        eva.setActive(true);
        eva.setCreateAt(LocalDateTime.now());
        eva.setLastInteraction(LocalDateTime.now());
        eva.setLevel(0);
        eva.setExperiencie(0);

        eS.create(eva);

        return ResponseEntity.ok("");
    }


    @PutMapping("/actuliza/{id}")
    public ResponseEntity<?> Update(@RequestBody EvaPetInsertDTO evaI, @PathVariable Long id){
        ModelMapper m = new ModelMapper();
        Optional<EvaPet> optional =eS.getById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Mascota no encontrada");
        }
        List<String> namesE = eS.getAllNames();
        if(namesE.contains(evaI.getName())){
            return ResponseEntity.badRequest().body("Nombre ocupado");
        }
        EvaPet myEva = optional.get();

            myEva.setName(evaI.getName());
            myEva.setDescription(evaI.getDescription());
            myEva.setStatus(evaI.getStatus());

            eS.update(myEva.getId(),myEva);

        EvaPetInsertDTO updated = m.map(myEva, EvaPetInsertDTO.class);


        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> Delete(@PathVariable Long id){
        ModelMapper m = new ModelMapper();
        Optional<EvaPet> optional = eS.getById(id);
        if (optional.isEmpty()){
            return ResponseEntity.badRequest().body("Elemento no encontrado");
        }
        EvaPet myEva = optional.get();
        myEva.setActive(false);
        eS.delete(myEva.getId(), myEva);

        return ResponseEntity.ok(myEva);
    }
}
