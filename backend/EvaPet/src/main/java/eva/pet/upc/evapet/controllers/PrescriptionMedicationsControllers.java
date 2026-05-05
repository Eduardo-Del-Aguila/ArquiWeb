package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.medications.MedicationUseDTO;
import eva.pet.upc.evapet.dtos.prescriptionmedications.PrescriptionMedicationsDTO;
import eva.pet.upc.evapet.dtos.prescriptionmedications.PrescriptionMedicationsInsertDTO;
import eva.pet.upc.evapet.models.PrescriptionMedications;
import eva.pet.upc.evapet.serviceInterfaces.IPrescriptionMedicationsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prescripcion-medicamentos")
public class PrescriptionMedicationsControllers {
    @Autowired
    private IPrescriptionMedicationsService pMS;

    @GetMapping("/listar")
    public ResponseEntity<List<PrescriptionMedicationsDTO>> listar() {
        ModelMapper m = new ModelMapper();

        List<PrescriptionMedicationsDTO> lista = pMS.list().stream()
                .map(y -> m.map(y, PrescriptionMedicationsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> registrar(@RequestBody PrescriptionMedicationsInsertDTO dto) {

        if (dto.getIdPrescription() <= 0) {
            return ResponseEntity.badRequest()
                    .body("El ID de la prescripción debe ser mayor a 0");
        }

        if (dto.getIdMedication() <= 0) {
            return ResponseEntity.badRequest()
                    .body("El ID del medicamento debe ser mayor a 0");
        }

        if (!pMS.existsPrescription(dto.getIdPrescription())) {
            return ResponseEntity.badRequest()
                    .body("La prescripción no existe");
        }

        if (!pMS.existsMedication(dto.getIdMedication())) {
            return ResponseEntity.badRequest()
                    .body("El medicamento no existe");
        }

        if (dto.getDose() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La dosis debe ser mayor a 0");
        }

        if (dto.getFrequency() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La frecuencia debe ser mayor a 0");
        }

        if (dto.getDuration() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La duración debe ser mayor a 0");
        }

        ModelMapper m = new ModelMapper();
        PrescriptionMedications pm = m.map(dto, PrescriptionMedications.class);

        PrescriptionMedications nuevo = pMS.insert(pm);

        PrescriptionMedicationsInsertDTO responseDTO =
                m.map(nuevo, PrescriptionMedicationsInsertDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/medicamentos-por-prescripcion/{id}")
    public ResponseEntity<?> listarPorPrescripcion(@PathVariable int id){

        if (!pMS.existsPrescription(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La prescripción no existe");
        }

        List<PrescriptionMedications> lista = pMS.findByPrescription(id);

        ModelMapper m = new ModelMapper();

        List<PrescriptionMedicationsDTO> respuesta = lista.stream()
                .map(x -> m.map(x, PrescriptionMedicationsDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestBody PrescriptionMedicationsInsertDTO dto) {

        Optional<PrescriptionMedications> existente =
                pMS.listId(dto.getIdPrescriptionMedications());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La relación no existe");
        }

        if (dto.getDose() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La dosis debe ser mayor a 0");
        }

        if (dto.getFrequency() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La frecuencia debe ser mayor a 0");
        }

        if (dto.getDuration() <= 0) {
            return ResponseEntity.badRequest()
                    .body("La duración debe ser mayor a 0");
        }

        PrescriptionMedications pm = existente.get();

        pm.setDose(dto.getDose());
        pm.setFrequency(dto.getFrequency());
        pm.setDuration(dto.getDuration());

        pMS.update(pm);

        return ResponseEntity.ok("Relación actualizada correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id){

        if (id <= 0) {
            return ResponseEntity.badRequest()
                    .body("ID inválido");
        }

        Optional<PrescriptionMedications> pm = pMS.listId(id);

        if(pm.isPresent()){
            pMS.delete(id);
            return ResponseEntity.ok("Medicamento eliminado de la prescripción");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro no encontrado");
        }
    }

    @GetMapping("/medicamentos-mas-usados")
    public ResponseEntity<?> MostUsedMedications() {

        List<Object[]> lista = pMS.MostUsedMedications();

        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay registros");
        }

        List<MedicationUseDTO> respuesta = new ArrayList<>();

        for(Object[] fila : lista){
            MedicationUseDTO dto = new MedicationUseDTO();

            dto.setName((String) fila[0]);
            dto.setTotalUso(((Number) fila[1]).intValue());

            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

}
