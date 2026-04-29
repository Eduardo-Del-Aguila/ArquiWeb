package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.models.Users;

import java.util.List;

public interface IUsersService {
    public List<Users> list();
    public Users insert(Users u);

}
