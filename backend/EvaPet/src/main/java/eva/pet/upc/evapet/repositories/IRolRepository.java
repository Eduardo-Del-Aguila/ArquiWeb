package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol,Long> {
}
