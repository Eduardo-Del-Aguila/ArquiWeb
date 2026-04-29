package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.Users;
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
    public List<Users> list() {
        return uR.findAll();
    }

    @Override
    public Users insert(Users u) {
        return uR.save(u);
    }

}
