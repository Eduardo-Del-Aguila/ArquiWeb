package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {

}