package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.mail = :mail")
    Optional<User> findUserByMail2(String mail);

    //Forma automática por nomnre (mail) sorprendente que detecte nombre
    Optional<User> findUserByMail(String mail);
}