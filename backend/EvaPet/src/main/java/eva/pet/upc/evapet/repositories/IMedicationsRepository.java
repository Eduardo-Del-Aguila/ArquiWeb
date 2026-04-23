package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Medications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicationsRepository extends JpaRepository<Medications,Integer> {
}
