package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    public List<Rol> list();


    public Optional<Rol> ListByName(UserRol nameRol);

    public Rol insert(Rol r);
    public Optional<Rol> listId(Long id);
    public void update(Rol r);
    public void delete(Long id);
}
