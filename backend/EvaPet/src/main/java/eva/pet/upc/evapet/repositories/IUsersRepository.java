package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {
}
