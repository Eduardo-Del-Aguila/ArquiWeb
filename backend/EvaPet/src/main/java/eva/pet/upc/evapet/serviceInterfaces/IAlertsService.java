package eva.pet.upc.evapet.serviceInterfaces;

import eva.pet.upc.evapet.models.Alerts;
import eva.pet.upc.evapet.models.Rol;

import java.util.List;
import java.util.Optional;

public interface IAlertsService {
    public List<Alerts> list();
    public Alerts insert(Alerts a);
    public Optional<Alerts> listId(Long id);
    public void update(Alerts a);
    public void delete(Long id);

}
