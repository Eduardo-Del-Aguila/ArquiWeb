package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Hospital;
import eva.pet.upc.evapet.models.User;

import java.util.List;
import java.util.Optional;

public interface IUsersService {
    public List<User> list();

    public User insert(User u);

    public Optional<User> listById(Long id);
    public User update(User u);
    public void deleteById(User u);

    public Optional<User> findUserByMail(String mail);


}
