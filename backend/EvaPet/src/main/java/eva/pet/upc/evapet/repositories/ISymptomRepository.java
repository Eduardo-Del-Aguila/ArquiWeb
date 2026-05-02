package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISymptomRepository extends JpaRepository<Symptom,Long> {
}
