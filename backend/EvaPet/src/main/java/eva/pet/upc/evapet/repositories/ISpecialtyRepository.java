package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialtyRepository extends JpaRepository<Specialty,Integer> {
}
