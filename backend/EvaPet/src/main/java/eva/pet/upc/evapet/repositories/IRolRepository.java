package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol,Long> {

    @Query("SELECT r FROM Rol r WHERE r.nameRol= :nameRol")
    Optional<Rol> findRolByNameRol(@Param("nameRol") UserRol nameRol);
}
