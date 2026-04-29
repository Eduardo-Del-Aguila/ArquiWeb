package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlertsRepository extends JpaRepository<Alerts, Long> {
}
