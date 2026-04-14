package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.SpecialtyDTO;
import eva.pet.upc.evapet.serviceInterfaces.ISpecialtyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpecialtyController {
    @Autowired
    private ISpecialtyService sS;
    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> listar(){
        ModelMapper m= new ModelMapper();
        List<SpecialtyDTO> listaSpecialties=sS.list().stream()
                .map(y ->m.map(y,SpecialtyDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaSpecialties);
    }
}
