package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import eva.pet.upc.evapet.serviceInterfaces.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImplement implements IUsersService {
    @Autowired
    private IUsersRepository uR;

    @Override
    public List<User> list() {
        return uR.findAll().stream().filter(User::isActive).toList();
    }

    @Override
    public User insert(User u) {
        return uR.save(u);
    }

    @Override
    public Optional<User> listById(Long id) {
        return uR.findById(id);
    }

    @Override
    public User update(User u) {
        return uR.save(u);
    }

    //Quizas se añada una eliminacion lógica como querie
    @Override
    public void deleteById(Long id) {

    }


}
