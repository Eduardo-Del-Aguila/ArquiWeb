package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {


}
