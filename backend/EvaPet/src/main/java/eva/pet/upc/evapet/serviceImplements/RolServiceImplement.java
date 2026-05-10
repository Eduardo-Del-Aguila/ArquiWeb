package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.enums.UserRol;
import eva.pet.upc.evapet.models.Rol;
import eva.pet.upc.evapet.repositories.IRolRepository;
import eva.pet.upc.evapet.serviceInterfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplement implements IRolService {
    @Autowired
    private IRolRepository rR;

    @Override
    public List<Rol> list() { return rR.findAll(); }

    @Override
    public Optional<Rol> ListByName(UserRol nameRol) {
        return rR.findRolByNameRol(nameRol);
    }

    @Override
    public Rol insert(Rol r) {
        return rR.save(r);
    }

    @Override
    public Optional<Rol> listId(Long id) {
        return rR.findById(id);
    }

    @Override
    public void delete(Long id) { rR.deleteById(id); }

    @Override
    public void update(Rol r) { rR.save(r); }

}


