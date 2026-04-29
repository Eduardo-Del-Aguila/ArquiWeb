package eva.pet.upc.evapet.repositories;

import eva.pet.upc.evapet.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {
}
