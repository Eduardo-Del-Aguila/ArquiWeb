package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrescriptionRespository extends JpaRepository<Prescription, Integer> {

}
