package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.MedicationsDTO;
import eva.pet.upc.evapet.dtos.MedicationsInsertDTO;
import eva.pet.upc.evapet.serviceInterfaces.IMedicationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MedicationsControllers {
    @Autowired
    private IMedicationsService mS;
    @GetMapping
    public ResponseEntity<List<MedicationsDTO>> listar(){
        ModelMapper m=new ModelMapper();
        List<MedicationsDTO> listaMedications=mS.list().stream()
                .map(y->m.map(y,MedicationsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaMedications);
    }

    @PostMapping("/web")
    public ResponseEntity<?> registrar(@RequestBody MedicationsInsertDTO dto){

        if (dto.() == null ) {
            return ResponseEntity.badRequest()
                    .body("Las fecha no pueden ser nulas");
        }
        if (!dto.getBirthDateAuthor().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body("La fecha debe ser anterior a la actual");
        }
        ModelMapper m=new ModelMapper();
        Author a=m.map(dto, Author.class);
        Author autor= aS.insert(a);
        AuthorInsertDTO responseDTO=m.map(autor,AuthorInsertDTO.class);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


}
